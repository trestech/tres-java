package com.trestechnologies.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.trestechnologies.api.interfaces.APIContext;
import com.trestechnologies.api.model.*;
import org.apache.http.conn.HttpHostConnectException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class TresContextTest extends BaseTestCase {
  JsonNode node;

  @SuppressWarnings("resource") // We're testing the constructor, not the close method.
  public void testAllConstructors ( ) throws ClassNotFoundException, IOException {
    Class<?> clazz = Class.forName("com.trestechnologies.api.TresContext");
    assertEquals("expect TresContext constructors size", 13, clazz.getConstructors().length);

    // Note, these will ping production servers, so we need to mock them out
    // later on.  These un-mocked tests will be minimal, intended only for
    // coverage.
    
    try ( TresContext context = new TresContext() ) {
      assertNotNull("expect not null version", context.version());
    } catch ( HttpHostConnectException e ) {
      fail("TresContext() failed: " + e.getMessage());
    };
    
    new TresContext(APIContext::close);
    try {
      new TresContext("username", "password", "domain", APIContext::close);
    } catch ( Exception ignore ) { }
    
    // Mocking out these calls.
    
    ((WithMockWebServer) context -> {
      String url = context.getUrl().toString();
      
      new TresContext(url);
      new TresContext(url, APIContext::close);
      try {
        new TresContext(url, "username", "password", "domain", APIContext::close);
      } catch ( Exception ignore ) { }
      try {
        new TresContext(url, context.getToken());
      } catch ( Exception ignore ) { }
      try {
        new TresContext(url, context.getToken(), "tres_context_all_constructors_token");
      } catch ( Exception ignore ) { }
      try {
        new TresContext(url, "username", "password", "domain");
      } catch ( Exception ignore ) { }
      try {
        new TresContext(url, "username", "password", "domain", APIContext::close);
      } catch ( Exception ignore ) { }
      try {
        new TresContext(url, "username", "password", "domain", "tres_context_all_constructors_username_password_domain");
      } catch ( Exception ignore ) { }
      try {
        new TresContext(new URL(url), "username", "password", "domain");
      } catch ( Exception ignore ) { }
    }).group("tres_context_all_constructors");
  }

  public void testAdminProductSearch ( ) { ((WithMockAdminWebServer) context -> {
    ObjectNode params = JsonNodeFactory.instance.objectNode();
    ArrayNode includeCols = params.putArray("includeCols");
    ObjectNode tramsId = params.putObject("tramsId");
    ArrayNode tramsIdValues = tramsId.putArray("value");
    JsonNode result, productResult;

    params.put("startingRow", 0);
    tramsIdValues.add(57052); // <- MAST TrinID on Dev Admin 57052 <-> CustomerProfile_RecNo 5058 <-> MST1
    tramsIdValues.add(24323);
    tramsIdValues.add(1951);
    tramsIdValues.add(41082);
    tramsIdValues.add(48800);
    includeCols.add("recNo");
    includeCols.add("tramsId");

    result = context.post("CustomerProfileSearch", params);

    //System.out.println(result);

    ObjectNode productParams = JsonNodeFactory.instance.objectNode();
    ArrayNode productIncludeCols = productParams.putArray("includeCols");
    ObjectNode customerProfileRecNo = productParams.putObject("CustomerProfile_recNo");
    ArrayNode customerProfileRecNos = customerProfileRecNo.putArray("value");

    productParams.put("startingRow", 0);
    productIncludeCols.add("recNo");
    productIncludeCols.add("productName");
    productIncludeCols.add("cboAlias");

    result.forEach(( JsonNode customer ) -> {
      customerProfileRecNos.add(customer.get("recNo"));
    });

    productResult = context.post("CustomerProductSearch", productParams);
    
    assert (productResult.size() > 0) : "expect productResult size > 0";
  }).group("tres_context_admin_product_search"); }
  
  public void testAdminProductSearchInvalid ( ) { ((WithMockAdminWebServer) context -> {
    ObjectNode params = JsonNodeFactory.instance.objectNode();
    ArrayNode includeCols = params.putArray("includeCols");
    ObjectNode tramsId = params.putObject("tramsId");
    ArrayNode tramsIdValues = tramsId.putArray("value");
    JsonNode result, productResult;

    params.put("startingRow", 0);
    tramsIdValues.add(987654321); // <- MAST TrinID on Dev Admin invalid
    includeCols.add("recNo");
    includeCols.add("tramsId");

    result = context.post("CustomerProfileSearch", params);
    assert result.isEmpty() : "expect result empty";
  }).group("tres_context_admin_product_search_invalid"); }
  
  public void testUrlInvalid ( ) { ((WithMockWebServer) context -> {
    try ( TresContext ctx = new TresContext("http://localhost:9876", null, "url_invalid") ) {
      fail("expect invalid url");
    } catch ( HttpHostConnectException e ) {
      // success
    }
  }).group("tres_context_url_invalid"); }

  public void testApiVersion ( ) { ((WithMockWebServer) context -> {
    assertSemanticVersion("1.0.17.8", context.getApiVersion());
  }).group("tres_context_api_version"); }

  private void assertSemanticVersion ( @SuppressWarnings("SameParameterValue") String expectedVersion, String actualVersion ) {
    String[] expectedSemanticVersion = expectedVersion.split("\\.");
    String[] actualSemanticVersion = actualVersion.split("\\.");
    
    assertEquals("major version", expectedSemanticVersion[0], actualSemanticVersion[0]);
    assertEquals("minor version", expectedSemanticVersion[1], actualSemanticVersion[1]);
    assertEquals("major build", expectedSemanticVersion[2], actualSemanticVersion[2]);
    assertEquals("minor build", expectedSemanticVersion[3], actualSemanticVersion[3]);
  }

  public void testAddCustomHeaders ( ) { ((WithMockWebServer) context -> {
    // Note, this header will replace bearer token.
    assertNotNull(context.get("Version", r -> r.setHeader("X-Test", "TEST")));
  }).group("tres_context_add_custom_headers"); }

  public void testMethodInvalid ( ) { ((WithMockWebServer) context -> {
    try {
      context.post("BOGUS");
    } catch ( TresException e ) {
      if ( e.getMessage().contains("Unable to map response") ) {
        // success on com.fasterxml.jackson.core:jackson-databind:2.0
      } else {
        assertEquals("HTTP/1.1 404 Not Found: POST " + context.getUrl() + "BOGUS HTTP/1.1 ", e.getMessage());
      }
      
    }
  }).group("tres_context_method_invalid"); }

  public void testLoginInvalidUsernamePassword ( ) { ((WithMockWebServer) context -> {
    try {
      context.login("BOGUS", "BOGUS", "0001");
      fail("expect invalid login");
    } catch ( TresException e ) {
      assertEquals("Invalid Username/Password", e.getMessage());
    }
  }).group("tres_context_login_invalid_username_password"); }

  public void testLoginInvalidAlias ( ) { ((WithMockWebServer) context -> {
    try {
      context.login("BOGUS", "BOGUS", "BOGUS");
      fail("expect invalid login");
    } catch ( TresException e ) {
      assertEquals("Invalid alias", e.getMessage());
    }
  }).group("tres_context_login_invalid_alias"); }

  public void testLoginMissingAlias ( ) { ((WithMockWebServer) context -> {
    try {
      context.login("BOGUS", "BOGUS", null);
      fail("expect invalid login");
    } catch ( TresException e ) {
      assert e.getMessage().contains("HTTP/1.1 404 Not Found: GET http://") : "expected 404, got " + e.getMessage();
    }
  }).group("tres_context_login_missing_alias"); }

  public void testRefreshIdentityToken ( ) { ((WithMockWebServer) context -> {
    assertEquals(0, context.refreshIdentityToken().get("resultCode").intValue());
  }).group("tres_context_refresh_identity_token"); }

  @SuppressWarnings("resource") // We're testing the constructor, not the close method.
  public void testBatch ( ) { ((WithMockWebServer) context -> {
    new TresContext(context.getUrl().toString(), context.getToken(), "tres_context_batch").batch((ctx) -> {
      ctx.refreshIdentityToken();
      ctx.refreshIdentityToken();
      ctx.refreshIdentityToken();
    });
  }).group("tres_context_batch"); }

  public void testRefreshIdentityTokenInvalid ( ) { ((WithMockWebServer) context -> {
    context.logout();
    
    try {
      assertEquals(0, context.refreshIdentityToken().get("resultCode").intValue());
    } catch ( TresException e ) {
      assertEquals("{\"resultCode\":302,\"resultDescription\":\"Identity token missing\",\"method\":\"identityToken\"}", e.getMessage());
    }
  }).group("tres_context_refresh_identity_token_invalid"); }

  public void testQueryPendingActivities ( ) { ((WithMockWebServer) context -> {
    int userRecNo = 1825;
    ActivitySearchParams params = new ActivitySearchParams();

    params.setAppUserRecNo(userRecNo);
    params.setCompleted(false);

    node = context.post("ActivitySearch", params);
    
    node.forEach((activity) -> {
      assertEquals(userRecNo, activity.get("appUser_recNo").intValue());
    });
  }).group("tres_context_query_pending_activities"); }

  public void testQueryPendingActivitiesMultipleAppUsers ( ) { ((WithMockWebServer) context -> {
    ActivitySearchParams params = new ActivitySearchParams();

    params.setStartingRow(0);
    params.setAppUserRecNo(new NumSearchParam(new ArrayList<Long>() {{
      add(1825L);
      add(1L);
    }}));
    params.setCompleted(false);

    node = context.post("ActivitySearch", params);

    assert(isNodeEmpty(node)) : "expect empty node";
  }).group("tres_context_query_pending_activities_multiple_app_users"); }

  public void testQueryPendingActivitiesAppUserBlank ( ) { ((WithMockWebServer) context -> {
    ActivitySearchParams params = new ActivitySearchParams();

    params.setStartingRow(0);
    params.setAppUserRecNo(NumSearchParam.Compare.BLANK);
    params.setCompleted(false);

    node = context.post("ActivitySearch", params);

    assert(isNodeEmpty(node));
  }).group("tres_context_query_pending_activities_app_user_blank"); }

  public void testQueryAccountingEntries ( ) { ((WithMockWebServer) context -> {
    ObjectNode params = JsonNodeFactory.instance.objectNode();

    params.put("startingRow", 0);

    node = context.post("AccountingEntrySearch", params);

    //node.forEach(System.out::println);

    assert(!isNodeEmpty(node));
  }).group("tres_context_query_accounting_entries"); }

  public void testTagCRUD ( ) { ((WithMockWebServer) context -> {
    ObjectNode payload = JsonNodeFactory.instance.objectNode();
    ObjectNode data = payload.putObject("oldNewDataset");
    ArrayNode oldData = data.putArray("oldData");
    ArrayNode newData = data.putArray("newData");
    Tag tag = new Tag();
    int tagRecNo;
    
    newData.addPOJO(tag);

    tag.setActiveStatus(true);
    tag.setValueFreeFlow(true);
    tag.setValueRequired(true);
    tag.setValueList("Java-TEST-Client");
    tag.setName("Java TEST Client");

    try {
//      node = context.post("Tag", payload); // create
//
//      assert (node.has("recNo"));
//      tagRecNo = node.get("recNo").intValue();
//
//      assertNotSame(0, tagRecNo);
    } catch ( TresException e ) {
      fail(e.getMessage());

      tagRecNo = -1;
    }

    ObjectNode params = JsonNodeFactory.instance.objectNode();
//    ObjectNode recNo = params.putObject("recNo");
//
//    recNo.put("value", 12877);
//    recNo.put("compareCondition", NumSearchParam.Compare.EQUAL.id);
    ObjectNode name = params.putObject("name");
    params.put("startingRow", 0);
    params.put("rowCount", 1);
    name.put("name", "Java-TEST-Client");
    name.put("compareCondition", StringSearchParam.Compare.EQUAL.id);

    node = context.post("TagSearch", params); // retrieve

    if ( !SKIP_PENDING_FIX ) {
      assertEquals("expect one record in this result", 1, node.size());
      // TODO Retrieve, Update, Delete
//      tagRecNo = 12876;
      payload = JsonNodeFactory.instance.objectNode();
      data = payload.putObject("oldNewDataset");
      oldData = data.putArray("oldData");
      newData = data.putArray("newData");

      oldData.addPOJO(tag);

      node = context.post("Tag", payload); // update
    }
  }).group("tres_context_tag_crud"); }
}
