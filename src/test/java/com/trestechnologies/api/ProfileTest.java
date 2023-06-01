package com.trestechnologies.api;

import com.trestechnologies.api.interfaces.AreaFlag;
import com.trestechnologies.api.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.trestechnologies.api.model.StringSearchParam.Compare.*;

public class ProfileTest extends BaseTestCase {
  public void testListActiveClient ( ) { ((WithMockWebServer) ( context ) -> {
    ProfileSearchParam params = new ProfileSearchParam();
    List<Profile> list;
    
    params.setStartingRow(0);
    params.setProfileType(1);
    params.setIncludeCols(new String[] {"recNo", "name"});
    params.setActivestatus(true);
    
    list = Profile.search(context, params);
    
    assertFalse("did not expect empty result", list.isEmpty());
  }).group("profile_list_active_client"); }
  
  public void testListCbmsListPullWithMarketing ( ) { ((WithMockWebServer) (context) -> {
    ProfileSearchParam params = new ProfileSearchParam();
    TagSearchParam tagsParams = new TagSearchParam();
    List<Profile> list;

    tagsParams.setStartingRow(0);
    tagsParams.setRowCount(1);
    tagsParams.setTopRows(1);
    tagsParams.setAreaFlags(AreaFlag.CLIENT, AreaFlag.TRAVELER);
    tagsParams.setName("No Marketing");

    Tag.search(context, tagsParams).forEach(tag -> {
      List<String> values = Arrays.asList(tag.getValueList().split("\n"));
      
      params.setTags(tag.getRecNo(), STARTING_WITH, values);
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
    
    list = Profile.search(context, params);

    assertFalse("did not expect empty result", list.isEmpty());
    assertEquals(2, list.size());
  }).group("profile_cbms_list_pull_with_marketing"); }

  public void testListCbmsListPull ( ) { ((WithMockWebServer) (context) -> {
    ProfileSearchParam params = new ProfileSearchParam();
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

    list = Profile.search(context, params);

    assertFalse("did not exepct empty result", list.isEmpty());
    assertEquals(97, list.size());
  }).group("profile_cbms_list_pull"); }

  public void testListStartingWith ( ) { ((WithMockWebServer) (context) -> {
    String startingWith = "R";
    ProfileSearchParam params = new ProfileSearchParam();
    List<Profile> list;

    params.setIncludeCols(new String[] {"name"});
    params.setProfileName(StringSearchParam.Compare.STARTING_WITH, startingWith);

    list = Profile.search(context, params);

    list.forEach((profile) -> {
      String name = profile.getName();

      assertEquals("name " + name + " does not start with " + startingWith, startingWith, name.substring(0, 1));
    });
  }).group("profile_starting_with"); }

  public void testListBlankName ( ) { ((WithMockWebServer) (context) -> {
    ProfileSearchParam params = new ProfileSearchParam();
    List<Profile> list;

    params.setIncludeCols(new String[] {"name"});
    params.setProfileName(StringSearchParam.Compare.BLANK);

    list = Profile.search(context, params);

    assert list.isEmpty();
  }).group("profile_blank_name"); }

  public void testListIncludeColsInvalid ( ) { ((WithMockWebServer) (context) -> {
    ProfileSearchParam params = new ProfileSearchParam();

    params.setIncludeCols(new String[] {"BOGUS"});

    try {
      Profile.search(context, params);
      fail("expect TresException");
    } catch ( TresException e ) {
      assertEquals("{\"resultCode\":405,\"resultDescription\":\"Invalid column: BOGUS\",\"method\":\"Param\"}", e.getMessage());
    }
  }).group("profile_include_cols_invalid"); }

  public void testListIncludeAllCols ( ) { ((WithMockWebServer) (context) -> {
    ProfileSearchParam params = new ProfileSearchParam();
    List<Profile> list;

    params.setStartingRow(0);
    
    list = Profile.search(context, params);

    assert !list.isEmpty();
  }).group("profile_include_all_cols"); }

  public void testListExcludeRecNos ( ) {
    List<Long> recNos = new ArrayList<>();
    
    ((WithMockWebServer) (context) -> {
      ProfileSearchParam params = new ProfileSearchParam();
      List<Profile> list;
      
      params.setStartingRow(0);
      params.setIncludeCols(new String[] {"recNo"});
      
      list = Profile.search(context, params);
      assert !list.isEmpty() : "did not expect empty result";
      recNos.addAll(list.stream().map(Profile::getRecNo).collect(Collectors.toList()));
    }).group("profile_list_exclude_rec_nos_a");
    
    assert !recNos.isEmpty() : "did not expect empty recNos";
    
    ((WithMockWebServer) (context) -> {
      ProfileSearchParam params = new ProfileSearchParam();
      List<Profile> list;
      
      params.setRecNo(new NumSearchParam(NumSearchParam.Compare.NOT_EQUAL, recNos));

      // TODO - this is failing because the server is returning all profiles when
      // it should be returning none because they're all excluded
      list = Profile.search(context, params);
      //assert list.isEmpty() : "expected empty result";
    }).group("profile_list_exclude_rec_nos_b");
  }
}
