package com.trestechnologies.api.model;

public class MarketingSearchParam {
  private Short affiliation;

  private Long marketingElementRecNo;

  private StringSearchParam marketingElementId;
  
  private Boolean checked;

  public MarketingSearchParam ( ) { }

  public MarketingSearchParam ( Short affiliation, Long marketingElementRecNo, Boolean checked ) {
    this.affiliation = affiliation;
    this.marketingElementRecNo = marketingElementRecNo;
    this.checked = checked;
  }

  public MarketingSearchParam ( Short affiliation, StringSearchParam marketingElementId, Boolean checked ) {
    this.affiliation = affiliation;
    this.marketingElementId = marketingElementId;
    this.checked = checked;
  }

  public MarketingSearchParam ( Short affiliation, String marketingElementId, Boolean checked ) {
    this.affiliation = affiliation;
    this.marketingElementId = new StringSearchParam(marketingElementId);
    this.checked = checked;
  }

  public short getAffiliation () { return affiliation; }

  public void setAffiliation ( short affiliation ) { this.affiliation = affiliation; }

  public Long getMarketingElementRecNo () { return marketingElementRecNo; }

  public void setMarketingElementRecNo ( Long marketingElementRecNo ) { this.marketingElementRecNo = marketingElementRecNo; }
  public void setMarketingElementRecNo ( long marketingElementRecNo ) { this.marketingElementRecNo = marketingElementRecNo; }

  public StringSearchParam getMarketingElementId () { return marketingElementId; }

  public void setMarketingElementId ( StringSearchParam marketingElementId ) { this.marketingElementId = marketingElementId; }

  public Boolean getChecked () { return checked; }

  public void setChecked ( Boolean checked ) { this.checked = checked; }
}
