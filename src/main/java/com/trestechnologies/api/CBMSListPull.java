package com.trestechnologies.api;

import com.trestechnologies.api.interfaces.APIContext;
import com.trestechnologies.api.interfaces.AreaFlag;
import com.trestechnologies.api.model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Dumps the Tres API schema for a method to stdout.
 */
public class CBMSListPull extends CommandLine {
  protected CBMSListPull () throws IOException {
    super();
  }

  protected List<Profile> queryProfiles ( APIContext ctx ) throws IOException {
    return queryProfiles(ctx, null, null);
  }
  
  protected List<Profile> queryProfiles ( APIContext ctx, String marketingPartnerId, List<Long> marketingPartnerIdRecNos ) throws IOException {
    ProfileSearchParam params = new ProfileSearchParam();
    
    params.setStartingRow(0);
    params.setEmailPermitMarketing(true);
    params.setActiveStatus(Profile.ActiveStatus.ACTIVE);
    params.setProfileType(Profile.Type.CLIENT);
    params.setClientType(Profile.ClientType.PERSONAL);
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
    
    if ( marketingPartnerId != null ) {
      BaseSearchModel.TagSearchParam tagSearchParams = new BaseSearchModel.TagSearchParam();
      
      tagSearchParams.setValue(new StringSearchParam(StringSearchParam.Compare.STARTING_WITH, marketingPartnerId));
      tagSearchParams.setRecNo(marketingPartnerIdRecNos.get(0));
      params.setTags(tagSearchParams);
    }

    return Profile.search(ctx, params);
  }
  
  public static void main ( String[] args ) throws IOException {
    CBMSListPull cmd = new CBMSListPull();
    
    assert args.length < 2 : "Too many arguments";
    
    if ( args.length == 0 ) {
      String command = cmd.bestCommand("./bin/cbms-list-pull.sh");

      System.out.println("Usage: " + command + " <Marketing Partner ID>");

      System.exit(1);
    }
    
    cmd.refreshToken();
    
    try ( TresContext ctx = new TresContext(cmd.url, cmd.token) ) {
      String marketingPartnerId = args[0];
      TagSearchParam tagSearchParams = new TagSearchParam();
      String priority;
      List<Long> excludedProfileRecNos = new ArrayList<>();
      List<Profile> profiles;
      List<Long> marketingPartnerIdRecNos = new ArrayList<>();
      
      tagSearchParams.setStartingRow(0);
      tagSearchParams.setRowCount(1);
      tagSearchParams.setTopRows(1);
      tagSearchParams.setIncludeCols(new String[] {"recNo", "valueList"});
      tagSearchParams.setAreaFlags(AreaFlag.CLIENT, AreaFlag.TRAVELER);

      tagSearchParams.setName("MarketingID");
      Tag.search(ctx, tagSearchParams).forEach(tag -> {
        marketingPartnerIdRecNos.add(tag.getRecNo());
      });
      
      tagSearchParams.setName("No Marketing");
      Tag.search(ctx, tagSearchParams).forEach(tag -> {
        List<String> values = Arrays.asList(tag.getValueList().split("\n"));
        ProfileSearchParam profileParams = new ProfileSearchParam();

        profileParams.setStartingRow(0);
        profileParams.setIncludeCols(new String[] {"recNo"});
        profileParams.setTags(tag.getRecNo(), StringSearchParam.Compare.STARTING_WITH, values);

        try {
          Profile.search(ctx, profileParams).forEach(profile -> {
            excludedProfileRecNos.add(profile.getRecNo());
          });
        } catch ( IOException e ) {
          throw new RuntimeException(e);
        }
      });
      
      if ( marketingPartnerId == null ) {
        priority = "0";
        profiles = cmd.queryProfiles(ctx);
      } else {
        profiles = cmd.queryProfiles(ctx, marketingPartnerId, marketingPartnerIdRecNos);
        
        if ( profiles.isEmpty() ) {
          priority = "1";
          profiles = cmd.queryProfiles(ctx);
        } else {
          priority = "0";
        }
      }

      profiles.stream().filter(profile -> !excludedProfileRecNos.contains(profile.getRecNo())).forEach(profile -> {
        StringBuilder builder = new StringBuilder();
        
        builder.append(priority).append("\t"); // priority // #0
        builder.append("\t"); // branchId // #1
        builder.append(profile.getRecNo()).append("\t"); // profileno // #2
        builder.append(profile.getClientInformalSalutation()).append("\t"); // salutation // #3
        builder.append(profile.getPrimaryPersonFirstName()).append("\t"); // firstname // #4
        builder.append(profile.getPrimaryPersonLastName()).append("\t"); // lastname // #5
        builder.append(profile.getPrimaryEmail()).append("\t"); // email // #6
        builder.append("Y").append("\t"); // permitmarket // #7
        builder.append(profile.getStateProvince()).append("\t"); // state // #8
        builder.append(profile.getCountry()).append("\t"); // country // #9
        builder.append(profile.getClientAdvisorProfileRecNo()).append("\t"); // primaryagentLinkno // #10
        builder.append(profile.getRecNo()).append("\t"); // vpk // #11
        builder.append(profile.getClientAdvisorProfileRecNo()).append("\t"); // paVpk // #12
        builder.append("\t"); // shipname // #13
        builder.append("\t"); // departdate // #14
        builder.append("\t"); // departcity // #15
        builder.append("\t"); // arrivecity // #16
        builder.append("\t"); // shipid // #17
        builder.append("\t"); // nights // #18

        System.out.println(new String(builder));
      });
    }
  }
}
