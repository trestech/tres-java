package com.trestechnologies.api.model;

public class AffiliationMarketingElement extends BaseModel {
  public static ModelMapper<AffiliationMarketingElement> mapper ( ) { return new ModelMapper<AffiliationMarketingElement>() {
    @Override public Class<AffiliationMarketingElement> getModel () { return AffiliationMarketingElement.class; }
  }; }
  
  private long affiliationMarketingCategory_recNo;
  
  private long recNo;

  private String name;

  private String id;

  public AffiliationMarketingElement ( ) { }

  public long getAffiliationMarketingCategory_recNo () {
    return affiliationMarketingCategory_recNo;
  }

  public void setAffiliationMarketingCategory_recNo ( long affiliationMarketingCategory_recNo ) {
    this.affiliationMarketingCategory_recNo = affiliationMarketingCategory_recNo;
  }

  public long getRecNo () { return recNo; }

  public void setRecNo ( long recNo ) { this.recNo = recNo; }

  public String getName () { return name; }

  public void setName ( String name ) { this.name = name; }

  public String getId () { return id; }

  public void setId ( String name ) { this.id = id; }
}
