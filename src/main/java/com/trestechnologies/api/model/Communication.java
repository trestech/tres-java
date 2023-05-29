package com.trestechnologies.api.model;

public class Communication extends BaseModel {
  public enum Type { UNKNOWN, PHONE, EMAIL, SOCIAL_MEDIA, WEB }
  
  private long recNo;
  private Type type;
  private String subType;
  private String value;
  private String countryDomain;
  private String cityArea;
  private String userSpecific;
  private String description;
  private boolean isPrimary;
  private boolean permitMarketing;
  private boolean isBillingContact;

  public long getRecNo () {
    return recNo;
  }

  public void setRecNo ( long recNo ) {
    this.recNo = recNo;
  }

  public Type getType () {
    return type;
  }

  public void setType ( Type type ) {
    this.type = type;
  }

  public String getSubType () {
    return subType;
  }

  public void setSubType ( String subType ) {
    this.subType = subType;
  }

  public String getValue () {
    return value;
  }

  public void setValue ( String value ) {
    this.value = value;
  }

  public String getCountryDomain () {
    return countryDomain;
  }

  public void setCountryDomain ( String countryDomain ) {
    this.countryDomain = countryDomain;
  }

  public String getCityArea () {
    return cityArea;
  }

  public void setCityArea ( String cityArea ) {
    this.cityArea = cityArea;
  }

  public String getUserSpecific () {
    return userSpecific;
  }

  public void setUserSpecific ( String userSpecific ) {
    this.userSpecific = userSpecific;
  }

  public String getDescription () {
    return description;
  }

  public void setDescription ( String description ) {
    this.description = description;
  }

  public boolean isPrimary () {
    return isPrimary;
  }

  public void setPrimary ( boolean primary ) {
    isPrimary = primary;
  }

  public boolean isPermitMarketing () {
    return permitMarketing;
  }

  public void setPermitMarketing ( boolean permitMarketing ) {
    this.permitMarketing = permitMarketing;
  }

  public boolean isBillingContact () {
    return isBillingContact;
  }

  public void setBillingContact ( boolean billingContact ) {
    isBillingContact = billingContact;
  }
}
