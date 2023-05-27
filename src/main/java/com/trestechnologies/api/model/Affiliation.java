package com.trestechnologies.api.model;

import java.util.List;

public class Affiliation {
  public static final ModelMapper<Affiliation> MAPPER = new ModelMapper<Affiliation>() {
    @Override public Class<Affiliation> getModel () { return Affiliation.class; }
  };

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
