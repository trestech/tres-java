package com.trestechnologies.api;

import com.trestechnologies.api.interfaces.AreaFlag;
import com.trestechnologies.api.model.*;

import java.util.Arrays;
import java.util.List;

import static com.trestechnologies.api.model.StringSearchParam.Compare.*;

public class ProfileSearchTest extends BaseTestCase {
  public void testListActiveClient ( ) { ((WithMockWebServer) ( context ) -> {
    ProfileSearchParams params = new ProfileSearchParams();
    List<Profile> list;
    
    params.setStartingRow(0);
    params.setProfileType(1);
    params.setIncludeCols(new String[] {"recNo", "name"});
    params.setActivestatus(true);
    
    list = Profile.MAPPER.treeToList(context.post("ProfileSearch", params));
    
    assertFalse("did not expect empty result", list.isEmpty());
  }).group("profile_search_list_active_client"); }
  
  public void testListCbmsListPullWithMarketing ( ) { ((WithMockWebServer) (context) -> {
    ProfileSearchParams params = new ProfileSearchParams();
    TagSearchParam tagsParams = new TagSearchParam();
    List<Profile> list;

    tagsParams.setStartingRow(0);
    tagsParams.setRowCount(1);
    tagsParams.setTopRows(1);
    tagsParams.setAreaFlags(AreaFlag.CLIENT, AreaFlag.TRAVELER);
    tagsParams.setName("No Marketing");

    Tag.MAPPER.treeToList(context.post("TagSearch", tagsParams)).forEach(tag -> {
      params.setTags(STARTING_WITH, Arrays.asList(tag.getValueList().split("\n")));
    });

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
    
    list = Profile.MAPPER.treeToList(context.post("ProfileSearch", params));

    assertFalse("did not expect empty result", list.isEmpty());
    assertEquals(2, list.size());
  }).group("profile_search_cbms_list_pull_with_marketing"); }

  public void testListCbmsListPull ( ) { ((WithMockWebServer) (context) -> {
    ProfileSearchParams params = new ProfileSearchParams();
    List<Profile> list;

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

    list = Profile.MAPPER.treeToList(context.post("ProfileSearch", params));

    assertFalse("did not exepct empty result", list.isEmpty());
    assertEquals(97, list.size());
  }).group("profile_search_cbms_list_pull"); }

  public void testListStartingWith ( ) { ((WithMockWebServer) (context) -> {
    String startingWith = "R";
    ProfileSearchParams params = new ProfileSearchParams();
    List<Profile> list;

    params.setIncludeCols(new String[] {"name"});
    params.setProfileName(StringSearchParam.Compare.STARTING_WITH, startingWith);

    list = Profile.MAPPER.treeToList(context.post("ProfileSearch", params));

    list.forEach((profile) -> {
      String name = profile.getName();

      assertEquals("name " + name + " does not start with " + startingWith, startingWith, name.substring(0, 1));
    });
  }).group("profile_search_starting_with"); }

  public void testListBlankName ( ) { ((WithMockWebServer) (context) -> {
    ProfileSearchParams params = new ProfileSearchParams();
    List<Profile> list;

    params.setIncludeCols(new String[] {"name"});
    params.setProfileName(StringSearchParam.Compare.BLANK);

    list = Profile.MAPPER.treeToList(context.post("ProfileSearch", params));

    assert list.isEmpty();
  }).group("profile_search_blank_name"); }

  public void testListIncludeColsInvalid ( ) { ((WithMockWebServer) (context) -> {
    ProfileSearchParams params = new ProfileSearchParams();

    params.setIncludeCols(new String[] {"BOGUS"});

    try {
      context.post("ProfileSearch", params);
      fail("expect TresException");
    } catch ( TresException e ) {
      assertEquals("{\"resultCode\":405,\"resultDescription\":\"Invalid column: BOGUS\",\"method\":\"Param\"}", e.getMessage());
    }
  }).group("profile_search_include_cols_invalid"); }
}
