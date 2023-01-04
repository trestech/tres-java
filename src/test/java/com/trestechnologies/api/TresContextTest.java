package com.trestechnologies.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.trestechnologies.api.interfaces.APIContext;
import com.trestechnologies.api.interfaces.Affiliation;
import com.trestechnologies.api.model.*;
import org.apache.http.conn.HttpHostConnectException;

import java.net.URL;

public class TresContextTest extends BaseTestCase {
  JsonNode node;

  public void testAllConstructors ( ) { ((WithMockWebServer) (context) -> {
    String url = context.getUrl().toString();

    // Note, these will ping production
    new TresContext();
    new TresContext(APIContext::close);
    try {
      new TresContext("username", "password", "domain", APIContext::close);
    } catch ( Exception ignore ) { }
    
    new TresContext(url);
    new TresContext(url, APIContext::close);
    try {
      new TresContext(url, "username", "password", "domain", APIContext::close);
    } catch ( Exception ignore ) { }
    try {
      new TresContext(url, context.getToken());
    } catch ( Exception ignore ) { }
    try {
      new TresContext(url, context.getToken(), "all_constructors");
    } catch ( Exception ignore ) { }
    try {
      new TresContext(url, "username", "password", "domain");
    } catch ( Exception ignore ) { }
    try {
      new TresContext(url, "username", "password", "domain", APIContext::close);
    } catch ( Exception ignore ) { }
    try {
      new TresContext(url, "username", "password", "domain", "all_constructors");
    } catch ( Exception ignore ) { }
    try {
      new TresContext(new URL(url), "username", "password", "domain");
    } catch ( Exception ignore ) { }
  }).group("all_constructors"); }
  
  public void testUrlInvalid ( ) { ((WithMockWebServer) (context) -> {
    try {
      new TresContext("http://localhost:9876", null, "url_invalid");
      fail("expect invalid url");
    } catch ( HttpHostConnectException e ) {
      // success
    }
  }).group("url_invalid"); }

  public void testApiVersion ( ) { ((WithMockWebServer) (context) -> {
    assertEquals("1.0.5.8", context.getApiVersion());
  }).group("api_version"); }

  public void testAddCustomHeaders ( ) { ((WithMockWebServer) (context) -> {
    // Note, this header will replace bearer token.
    assertNotNull(context.get("Version", r -> r.setHeader("X-Test", "TEST")));
  }).group("add_custom_headers"); }

  public void testMethodInvalid ( ) { ((WithMockWebServer) (context) -> {
    try {
      context.post("BOGUS");
    } catch ( TresException e ) {
      if ( e.getMessage().contains("Unable to map response") ) {
        // success on com.fasterxml.jackson.core:jackson-databind:2.0
      } else {
        assertEquals("HTTP/1.1 404 Not Found: POST " + context.getUrl() + "BOGUS HTTP/1.1 ", e.getMessage());
      }
      
    }
  }).group("method_invalid"); }

  public void testLoginInvalidUsernamePassword ( ) { ((WithMockWebServer) (context) -> {
    try {
      context.login("BOGUS", "BOGUS", "0001");
      fail("expect invalid login");
    } catch ( TresException e ) {
      assertEquals("Invalid Username/Password", e.getMessage());
    }
  }).group("login_invalid_username_password"); }

  public void testLoginInvalidAlias ( ) { ((WithMockWebServer) (context) -> {
    try {
      context.login("BOGUS", "BOGUS", "BOGUS");
      fail("expect invalid login");
    } catch ( TresException e ) {
      assertEquals("Invalid alias", e.getMessage());
    }
  }).group("login_invalid_alias"); }

  public void testLoginMissingAlias ( ) { ((WithMockWebServer) (context) -> {
    try {
      context.login("BOGUS", "BOGUS", null);
      fail("expect invalid login");
    } catch ( TresException e ) {
      assertEquals("Invalid alias", e.getMessage());
    }
  }).group("login_missing_alias"); }

  public void testRefreshIdentityToken ( ) { ((WithMockWebServer) (context) -> {
    assertEquals(0, context.refreshIdentityToken().get("resultCode").intValue());
  }).group("refresh_identity_token"); }

  public void testBatch ( ) { ((WithMockWebServer) (context) -> {
    new TresContext(context.getUrl().toString(), context.getToken()).batch((ctx) -> {
      ctx.refreshIdentityToken();
      ctx.refreshIdentityToken();
      ctx.refreshIdentityToken();
    });
  }).group("batch"); }

  public void testRefreshIdentityTokenInvalid ( ) { ((WithMockWebServer) (context) -> {
    context.logout();
    
    try {
      assertEquals(0, context.refreshIdentityToken().get("resultCode").intValue());
    } catch ( TresException e ) {
      assertEquals("{\"resultCode\":302,\"resultDescription\":\"Identity token missing\",\"method\":\"identityToken\"}", e.getMessage());
    }
  }).group("refresh_identity_token_invalid"); }

  public void testCbmsListPullWithMarketing ( ) { ((WithMockWebServer) (context) -> {
    ProfileSearchParams params = new ProfileSearchParams();

    params.setStartingRow(0);
    params.setEmailPermitMarketing(true);
    params.setIncludeCols(new String[] {
      "recNo",
      "clientInformalSalutation",
      "primaryPersonFirstName",
      "primaryPersonLastName",
      "primaryEmail",
      "primaryEmailPermitMarketing",
      "stateProvince",
      "country",
      "clientAdvisorProfileRecNo"
    });
    params.setMarketing(new MarketingSearchParam[] {
      new MarketingSearchParam(Affiliation.TRES, "NOCBM", true),
      new MarketingSearchParam(Affiliation.TRES, "NOTBM", true),
      new MarketingSearchParam(Affiliation.TRES, "NOMKT", true),
      new MarketingSearchParam(Affiliation.TRES, "NOEMM", true),
      new MarketingSearchParam(Affiliation.TRES, "BDATA", true),
    });

    node = context.post("ProfileSearch", params);

    assertTrue("exepct empty result", isNodeEmpty(node));
    assertEquals(0, node.size());
  }).group("cbms_list_pull_with_marketing"); }

  public void testCbmsListPull ( ) { ((WithMockWebServer) (context) -> {
    ProfileSearchParams params = new ProfileSearchParams();

    params.setStartingRow(0);
    params.setEmailPermitMarketing(true);
    params.setIncludeCols(new String[] {
      "recNo",
      "clientInformalSalutation",
      "primaryPersonFirstName",
      "primaryPersonLastName",
      "primaryEmail",
      "primaryEmailPermitMarketing",
      "stateProvince",
      "country",
      "clientAdvisorProfileRecNo"
    });
    
    node = context.post("ProfileSearch", params);

    assertFalse("did not exepct empty result", isNodeEmpty(node));
    assertEquals(95, node.size());
  }).group("cbms_list_pull"); }
  
  public void testStartingWith ( ) { ((WithMockWebServer) (context) -> {
    String startingWith = "R";
    ProfileSearchParams params = new ProfileSearchParams();

    params.setIncludeCols(new String[] {"name"});
    params.setProfileName(StringSearchParam.Compare.STARTING_WITH, startingWith);

    node = context.post("ProfileSearch", params);

    node.forEach((profile) -> {
      String name = profile.get("name").textValue(); 
      
      assertEquals("name " + name + " does not start with " + startingWith, startingWith, name.substring(0, 1));
    });
  }).group("starting_with"); }

  public void testBlank ( ) { ((WithMockWebServer) (context) -> {
    ProfileSearchParams params = new ProfileSearchParams();

    params.setIncludeCols(new String[] {"name"});
    params.setProfileName(StringSearchParam.Compare.BLANK);

    node = context.post("ProfileSearch", params);

    assert(isNodeEmpty(node));
  }).group("blank"); }

  public void testIncludeColsInvalid ( ) { ((WithMockWebServer) (context) -> {
    ProfileSearchParams params = new ProfileSearchParams();

    params.setIncludeCols(new String[] {"BOGUS"});

    try {
      context.post("ProfileSearch", params);
      fail("expect TresException");
    } catch ( TresException e ) {
      assertEquals("{\"resultCode\":405,\"resultDescription\":\"Invalid column: BOGUS\",\"method\":\"Param\"}", e.getMessage());
    }
  }).group("include_cols_invalid"); }

  public void testQueryPendingActivities ( ) { ((WithMockWebServer) (context) -> {
    int userRecNo = 1825;
    ActivitySearchParams params = new ActivitySearchParams();

    params.setAppUserRecNo(userRecNo);
    params.setCompleted(false);

    node = context.post("ActivitySearch", params);
    
    node.forEach((activity) -> {
      assertEquals(userRecNo, activity.get("appUser_recNo").intValue());
    });
  }).group("query_pending_activities"); }

  public void testQueryPendingActivitiesMultipleAppUsers ( ) { ((WithMockWebServer) (context) -> {
    ActivitySearchParams params = new ActivitySearchParams();

    params.setStartingRow(0);
    params.setAppUserRecNo(new NumSearchParam(new Long[] {1825L, 1L}));
    params.setCompleted(false);

    node = context.post("ActivitySearch", params);

    assert(isNodeEmpty(node)) : "expect empty node";
  }).group("query_pending_activities_multiple_app_users"); }

  public void testQueryPendingActivitiesAppUserBlank ( ) { ((WithMockWebServer) (context) -> {
    ActivitySearchParams params = new ActivitySearchParams();

    params.setStartingRow(0);
    params.setAppUserRecNo(NumSearchParam.Compare.BLANK);
    params.setCompleted(false);

    node = context.post("ActivitySearch", params);

    assert(isNodeEmpty(node));
  }).group("query_pending_activities_app_user_blank"); }

  public void testQueryAccountingEntries ( ) { ((WithMockWebServer) (context) -> {
    ObjectNode params = JsonNodeFactory.instance.objectNode();

    params.put("startingRow", 0);

    node = context.post("AccountingEntrySearch", params);

//    node.forEach(System.out::println);

    assert(!isNodeEmpty(node));
  }).group("query_accounting_entries"); }

  public void testQueryTags ( ) { ((WithMockWebServer) (context) -> {
    ObjectNode params = JsonNodeFactory.instance.objectNode();

    params.put("startingRow", 0);

    node = context.post("TagSearch", params);

//  node.forEach(System.out::println);

    assert (!isNodeEmpty(node));
  }).group("query_tags"); }

  public void testTagCRUD ( ) { ((WithMockWebServer) (context) -> {
    ObjectNode payload = JsonNodeFactory.instance.objectNode();
    ObjectNode data = payload.putObject("oldNewDataset");
    ArrayNode oldData = data.putArray("oldData");
    ArrayNode newData = data.putArray("newData");
    ObjectNode tag = newData.addObject();
    int tagRecNo;

    tag.put("activeStatus", true);
    tag.put("valueFreeFlow", true);
    tag.put("valueRequired", true);
    tag.put("valueList", "Java-TEST-Client");
    tag.put("description", "Java TEST Client");

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

      oldData.add(tag);

      node = context.post("Tag", payload); // update
    }
  }).group("tag_crud"); }
}
