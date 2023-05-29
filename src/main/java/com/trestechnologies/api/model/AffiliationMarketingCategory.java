package com.trestechnologies.api.model;

import java.util.List;

public class AffiliationMarketingCategory extends BaseModel {
  public static ModelMapper<AffiliationMarketingCategory> mapper ( ) { return new ModelMapper<AffiliationMarketingCategory>() {
    @Override public Class<AffiliationMarketingCategory> getModel () { return AffiliationMarketingCategory.class; }
  }; }
  
  private long affiliation_recNo;
  
  private long recNo;
  
  private String name;
  
  private String id;
  
  private List<AffiliationMarketingElement> affiliationMarketingElement;
  
  public AffiliationMarketingCategory ( ) { }
  
  public long getAffiliation_recNo () { return affiliation_recNo; }
  
  public void setAffiliation_recNo ( long affiliation_recNo ) { this.affiliation_recNo = affiliation_recNo; }
  
  public long getRecNo () { return recNo; }
  
  public void setRecNo ( long recNo ) { this.recNo = recNo; }
  
  public String getName () { return name; }
  
  public void setName ( String name ) { this.name = name; }
  
  public String getId () { return id; }
  
  public void setId ( String name ) { this.id = id; }
  
  public List<AffiliationMarketingElement> getAffiliationMarketingElement () { return affiliationMarketingElement; }
  
  public void setAffiliationMarketingElement ( List<AffiliationMarketingElement> affiliationMarketingElement ) { this.affiliationMarketingElement = affiliationMarketingElement; }
}
