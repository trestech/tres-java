package com.trestechnologies.api.model;

import com.trestechnologies.api.interfaces.Affiliation;

public class MarketingSearchParam {
  private Short affiliation;

  private Long marketingElementRecNo;

  private String marketingElementId;
  
  private Boolean checked;

  public MarketingSearchParam ( Affiliation affiliation, long marketingElementRecNo, boolean checked ) {
    this.affiliation = affiliation.id;
    this.marketingElementRecNo = marketingElementRecNo;
    this.checked = checked;
  }

  public MarketingSearchParam ( Affiliation affiliation, String marketingElementId, boolean checked ) {
    this.affiliation = affiliation.id;
    this.marketingElementId = marketingElementId;
    this.checked = checked;
  }

  public Short getAffiliation () { return affiliation; }

  public void setAffiliation ( Short affiliation ) { this.affiliation = affiliation; }

  public Long getMarketingElementRecNo () { return marketingElementRecNo; }

  public void setMarketingElementRecNo ( Long marketingElementRecNo ) { this.marketingElementRecNo = marketingElementRecNo; }
  public void setMarketingElementRecNo ( long marketingElementRecNo ) { this.marketingElementRecNo = marketingElementRecNo; }

  public String getMarketingElementId () { return marketingElementId; }

  public void setMarketingElementId ( String marketingElementId ) { this.marketingElementId = marketingElementId; }

  public Boolean getChecked () { return checked; }

  public void setChecked ( Boolean checked ) { this.checked = checked; }
}
