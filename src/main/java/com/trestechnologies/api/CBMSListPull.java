package com.trestechnologies.api;

import com.trestechnologies.api.interfaces.APIContext;
import com.trestechnologies.api.interfaces.AreaFlag;
import com.trestechnologies.api.model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.ObjIntConsumer;

import static com.trestechnologies.api.model.StringSearchParam.Compare.NOT_BLANK;
import static java.lang.System.out;

/**
 * Dumps the Tres API schema for a method to stdout.
 */
public class CBMSListPull extends CommandLine {
  public static final String TAG_MARKETING_PROMO_ID = "Marketing PromoID";
  public static final String TAG_MARKETING_EXCLUSIONS = "Marketing Exclusions";
  public static final int PRIORITY_0 = 0;
  public static final int PRIORITY_1 = 1;
  
  private String includeTagName = TAG_MARKETING_PROMO_ID;
  
  private String excludeTagName = TAG_MARKETING_EXCLUSIONS;
  
  private String marketingPartnerId;
  
  protected CBMSListPull () throws IOException {
    super();
  }

  public CBMSListPull ( APIContext context ) {
    this(context, null);
  }
  
  public CBMSListPull ( APIContext context, String marketingPartnerId ) {
    super(context);
    
    this.marketingPartnerId = marketingPartnerId;
  }
  
  protected List<Profile> queryProfiles ( APIContext ctx ) throws IOException {
    return queryProfiles(ctx, null, null);
  }
  
  protected List<Profile> queryProfiles ( APIContext ctx, String marketingPartnerId, List<Long> marketingPartnerIdRecNos ) throws IOException {
    List<Profile> list;
    ProfileSearchParam params = new ProfileSearchParam();
    
    params.setStartingRow(0);
    params.setEmailPermitMarketing(true);
    params.setActiveStatus(Profile.ActiveStatus.ACTIVE);
    params.setProfileType(Profile.Type.CLIENT);
    params.setClientType(Profile.ClientType.PERSONAL);
    params.setStateProvince(NOT_BLANK);
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
    
    if ( marketingPartnerId != null && !marketingPartnerIdRecNos.isEmpty() ) {
      BaseSearchModel.TagSearchParam tagSearchParams = new BaseSearchModel.TagSearchParam();
      
      tagSearchParams.setValue(new StringSearchParam(StringSearchParam.Compare.STARTING_WITH, marketingPartnerId));
      tagSearchParams.setRecNo(marketingPartnerIdRecNos.get(0));
      params.setTags(tagSearchParams);
    }
    
    list = Profile.search(ctx, params);
    
    list = list.stream().filter(profile -> {
      if ( profile.getStateProvince() == null ) {
        return false;
      } else if ( profile.getStateProvince().trim().isEmpty() ) {
        // Even though we asked the API for non-blank state/province, we may still
        // get some profiles with whitespace and/or empty strings.  It's possible
        // that the API considers <null> as blank and empty strings as non-blank.
        
        return false;
      }
      
      return true;
    }).collect(java.util.stream.Collectors.toList());
    
    return list;
  }
  
  public void forEach ( ObjIntConsumer<Profile> action ) throws IOException {
    forEach(context, marketingPartnerId, action);
  }
  
  public void forEach ( String marketingPartnerId, ObjIntConsumer<Profile> action ) throws IOException {
    forEach(context, marketingPartnerId, action);
  }
  
  public void forEach ( APIContext context, String marketingPartnerId, ObjIntConsumer<Profile> action ) throws IOException {
    TagSearchParam tagSearchParams = new TagSearchParam();
    int priority;
    List<Long> excludedProfileRecNos = new ArrayList<>();
    List<Profile> profiles;
    List<Long> marketingPartnerIdRecNos = new ArrayList<>();

    tagSearchParams.setStartingRow(0);
    tagSearchParams.setRowCount(1);
    tagSearchParams.setTopRows(1);
    tagSearchParams.setIncludeCols(new String[] {"recNo", "valueList"});
    tagSearchParams.setAreaFlags(AreaFlag.CLIENT, AreaFlag.TRAVELER);

    tagSearchParams.setName(includeTagName);
    Tag.search(context, tagSearchParams).forEach(tag -> {
      marketingPartnerIdRecNos.add(tag.getRecNo());
    });

    tagSearchParams.setName(excludeTagName);
    Tag.search(context, tagSearchParams).forEach(tag -> {
      List<String> values = Arrays.asList(tag.getValueList().split("\n"));
      ProfileSearchParam profileParams = new ProfileSearchParam();

      profileParams.setStartingRow(0);
      profileParams.setIncludeCols(new String[] {"recNo"});
      profileParams.setTags(tag.getRecNo(), StringSearchParam.Compare.STARTING_WITH, values);

      try {
        Profile.search(context, profileParams).forEach(profile -> {
          excludedProfileRecNos.add(profile.getRecNo());
        });
      } catch ( IOException e ) {
        throw new RuntimeException(e);
      }
    });

    if ( marketingPartnerId == null ) {
      priority = PRIORITY_0;
      profiles = queryProfiles(context);
    } else {
      profiles = queryProfiles(context, marketingPartnerId, marketingPartnerIdRecNos);

      if ( profiles.isEmpty() ) {
        priority = PRIORITY_1;
        profiles = queryProfiles(context);
      } else {
        priority = PRIORITY_0;
      }
    }

    profiles.stream().filter(profile -> !excludedProfileRecNos.contains(profile.getRecNo())).forEach(profile -> {
      action.accept(profile, priority);
    });
  }

  public String getIncludeTagName () {
    return includeTagName;
  }

  public void setIncludeTagName ( String includeTagName ) {
    this.includeTagName = includeTagName;
  }

  public String getExcludeTagName () {
    return excludeTagName;
  }

  public void setExcludeTagName ( String excludeTagName ) {
    this.excludeTagName = excludeTagName;
  }

  public static void main ( String[] args ) throws IOException {
    CBMSListPull cmd = new CBMSListPull();
    
    if ( args.length == 0 ) {
      String command = cmd.bestCommand("./bin/cbms-list-pull.sh");

      out.println("Usage: " + command + " <Marketing Partner ID>");

      System.exit(1);
    }
    
    cmd.refreshToken();
    
    try ( TresContext ctx = new TresContext(cmd.url, cmd.token) ) {
      cmd.forEach(ctx, args[0], (profile, priority) -> {
        StringBuilder builder = new StringBuilder();
        
        builder.append(priority).append("\t"); // priority // #0
        builder.append("\t"); // branchId // #1
        builder.append(profile.getRecNo()).append("\t"); // profileno // #2
        builder.append(profile.getClientInformalSalutation()).append("\t"); // salutation // #3
        builder.append(profile.getPrimaryPersonFirstName()).append("\t"); // firstname // #4
        builder.append(profile.getPrimaryPersonLastName()).append("\t"); // lastname // #5
        builder.append(profile.getPrimaryEmail()).append("\t"); // email // #6
        builder.append(profile.isPrimaryEmailPermitMarketing() ? "Y" : "N").append("\t"); // permitmarket // #7
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

        out.println(new String(builder));
      });
    }
  }
}
