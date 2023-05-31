package com.trestechnologies.api.model;

import com.trestechnologies.api.interfaces.APIContext;

import java.io.IOException;
import java.util.List;

public class Affiliation extends BaseModel {
  public static ModelMapper<Affiliation> mapper ( ) { return new ModelMapper<Affiliation>() {
    @Override public Class<Affiliation> getModel () { return Affiliation.class; }
  }; }

  public static Affiliation find ( APIContext context, long recNo ) throws IOException {
    List<Affiliation> list = mapper().treeToList(context.get("Affiliation/" + recNo));
    
    return list.isEmpty() ? null : list.get(0);
  }

  private long recNo;

  private String name;

  private List<AffiliationMarketingCategory> affiliationMarketingCategory;
  
  public Affiliation ( ) { }

  public long getRecNo () { return recNo; }

  public void setRecNo ( long recNo ) { this.recNo = recNo; }

  public String getName () { return name; }

  public void setName ( String name ) { this.name = name; }

  public List<AffiliationMarketingCategory> getAffiliationMarketingCategory () {
    return affiliationMarketingCategory;
  }

  public void setAffiliationMarketingCategory ( List<AffiliationMarketingCategory> affiliationMarketingCategory ) {
    this.affiliationMarketingCategory = affiliationMarketingCategory;
  }
}
