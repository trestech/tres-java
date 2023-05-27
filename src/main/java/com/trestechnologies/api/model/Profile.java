package com.trestechnologies.api.model;

import java.util.Date;

public class Profile {
  public static final ModelMapper<Profile> MAPPER = new ModelMapper<Profile>() {
    @Override
    public Class<Profile> getModel () {
      return Profile.class;
    }
  };
  
  public enum Type { UNKNOWN, CLIENT, SUPPLIER, ADVISOR, OTHER }
  
  public enum ClientType { UNKNOWN, PERSONAL, CORPORATE }
  
  public enum SupplierType { UNKNOWN, BILLING_AND_SERVICE_PROVIDER, BILLING_PROVIDER, SERVICE_PROVIDER }
  
  public enum ActiveStatus {
    INACTIVE, ACTIVE, PENDING;
  }
  
  private long recNo;
  private long tagRecNo;
  private String tagName;
  private String tagValue;
  private int summaryCount;
  private Date createDateTime;
  private Date lastModifiedDateTime;
  private String name;
  private Type profileType;
  private ClientType clientType;
  private SupplierType supplierType;
  private long primaryPersonRecNo;
  private String primaryPersonName;
  private String primaryPersonFirstName;
  private String primaryPersonLastName;
  private String primaryPersonEmail;
  private String primaryPersonPhone;
  private short clientAnniversaryDay;
  private short clientAnniversaryMonth;
  private short clientAnniversaryYear;
  private String clientAnniversaryNote;
  private long clientAdvisorProfileRecNo;
  private String clientAdvisorName;
  private long clientBranchRecNo;
  private String clientBranchName;
  private String remarks;
  private ActiveStatus activeStatus;
  private String primaryEmail;
  private boolean primaryEmailPermitMarketing;
  private String primaryPhone;
  private boolean primaryPhonePermitMarketing;
  private boolean preferredSupplier;
  private int supplierTravelCategoryFlags;
  private String street1;
  private String street2;
  private String city;
  private String stateProvince;
  private String zipPostalCode;
  private String country;
  private String mailingStreet1;
  private String mailingStreet2;
  private String mailingCity;
  private String mailingStateProvince;
  private String mailingZipPostalCode;
  private String mailingCountry;
  private boolean addressPermitMarketing;
  private String supplierVendorId;
  private short supplierCommissionRate;
  private String clientInformalSalutation;
  private String clientFormalSalutation;
  private String firstId;
  private Communication.Type profileCommType;
  private String profileCommValue;
  private boolean profileCommIsBillingContact;
  
  

  public long getRecNo () {
    return recNo;
  }

  public void setRecNo ( long recNo ) {
    this.recNo = recNo;
  }

  public long getTagRecNo () {
    return tagRecNo;
  }

  public void setTagRecNo ( long tagRecNo ) {
    this.tagRecNo = tagRecNo;
  }

  public String getTagName () {
    return tagName;
  }

  public void setTagName ( String tagName ) {
    this.tagName = tagName;
  }

  public String getTagValue () {
    return tagValue;
  }

  public void setTagValue ( String tagValue ) {
    this.tagValue = tagValue;
  }

  public int getSummaryCount () {
    return summaryCount;
  }

  public void setSummaryCount ( int summaryCount ) {
    this.summaryCount = summaryCount;
  }

  public Date getCreateDateTime () {
    return createDateTime;
  }

  public void setCreateDateTime ( Date createDateTime ) {
    this.createDateTime = createDateTime;
  }

  public Date getLastModifiedDateTime () {
    return lastModifiedDateTime;
  }

  public void setLastModifiedDateTime ( Date lastModifiedDateTime ) {
    this.lastModifiedDateTime = lastModifiedDateTime;
  }

  public String getName () {
    return name;
  }

  public void setName ( String name ) {
    this.name = name;
  }

  public Type getProfileType () {
    return profileType;
  }

  public void setProfileType ( Type profileType ) {
    this.profileType = profileType;
  }

  public ClientType getClientType () {
    return clientType;
  }

  public void setClientType ( ClientType clientType ) {
    this.clientType = clientType;
  }

  public SupplierType getSupplierType () {
    return supplierType;
  }

  public void setSupplierType ( SupplierType supplierType ) {
    this.supplierType = supplierType;
  }

  public long getPrimaryPersonRecNo () {
    return primaryPersonRecNo;
  }

  public void setPrimaryPersonRecNo ( long primaryPersonRecNo ) {
    this.primaryPersonRecNo = primaryPersonRecNo;
  }

  public String getPrimaryPersonName () {
    return primaryPersonName;
  }

  public void setPrimaryPersonName ( String primaryPersonName ) {
    this.primaryPersonName = primaryPersonName;
  }

  public String getPrimaryPersonFirstName () {
    return primaryPersonFirstName;
  }

  public void setPrimaryPersonFirstName ( String primaryPersonFirstName ) {
    this.primaryPersonFirstName = primaryPersonFirstName;
  }

  public String getPrimaryPersonLastName () {
    return primaryPersonLastName;
  }

  public void setPrimaryPersonLastName ( String primaryPersonLastName ) {
    this.primaryPersonLastName = primaryPersonLastName;
  }

  public String getPrimaryPersonEmail () {
    return primaryPersonEmail;
  }

  public void setPrimaryPersonEmail ( String primaryPersonEmail ) {
    this.primaryPersonEmail = primaryPersonEmail;
  }

  public String getPrimaryPersonPhone () {
    return primaryPersonPhone;
  }

  public void setPrimaryPersonPhone ( String primaryPersonPhone ) {
    this.primaryPersonPhone = primaryPersonPhone;
  }

  public short getClientAnniversaryDay () {
    return clientAnniversaryDay;
  }

  public void setClientAnniversaryDay ( short clientAnniversaryDay ) {
    this.clientAnniversaryDay = clientAnniversaryDay;
  }

  public short getClientAnniversaryMonth () {
    return clientAnniversaryMonth;
  }

  public void setClientAnniversaryMonth ( short clientAnniversaryMonth ) {
    this.clientAnniversaryMonth = clientAnniversaryMonth;
  }

  public short getClientAnniversaryYear () {
    return clientAnniversaryYear;
  }

  public void setClientAnniversaryYear ( short clientAnniversaryYear ) {
    this.clientAnniversaryYear = clientAnniversaryYear;
  }

  public String getClientAnniversaryNote () {
    return clientAnniversaryNote;
  }

  public void setClientAnniversaryNote ( String clientAnniversaryNote ) {
    this.clientAnniversaryNote = clientAnniversaryNote;
  }

  public long getClientAdvisorProfileRecNo () {
    return clientAdvisorProfileRecNo;
  }

  public void setClientAdvisorProfileRecNo ( long clientAdvisorProfileRecNo ) {
    this.clientAdvisorProfileRecNo = clientAdvisorProfileRecNo;
  }

  public String getClientAdvisorName () {
    return clientAdvisorName;
  }

  public void setClientAdvisorName ( String clientAdvisorName ) {
    this.clientAdvisorName = clientAdvisorName;
  }

  public long getClientBranchRecNo () {
    return clientBranchRecNo;
  }

  public void setClientBranchRecNo ( long clientBranchRecNo ) {
    this.clientBranchRecNo = clientBranchRecNo;
  }

  public String getClientBranchName () {
    return clientBranchName;
  }

  public void setClientBranchName ( String clientBranchName ) {
    this.clientBranchName = clientBranchName;
  }

  public String getRemarks () {
    return remarks;
  }

  public void setRemarks ( String remarks ) {
    this.remarks = remarks;
  }

  public ActiveStatus getActiveStatus () {
    return activeStatus;
  }

  public void setActiveStatus ( ActiveStatus activeStatus ) {
    this.activeStatus = activeStatus;
  }

  public String getPrimaryEmail () {
    return primaryEmail;
  }

  public void setPrimaryEmail ( String primaryEmail ) {
    this.primaryEmail = primaryEmail;
  }

  public boolean isPrimaryEmailPermitMarketing () {
    return primaryEmailPermitMarketing;
  }

  public void setPrimaryEmailPermitMarketing ( boolean primaryEmailPermitMarketing ) {
    this.primaryEmailPermitMarketing = primaryEmailPermitMarketing;
  }

  public String getPrimaryPhone () {
    return primaryPhone;
  }

  public void setPrimaryPhone ( String primaryPhone ) {
    this.primaryPhone = primaryPhone;
  }

  public boolean isPrimaryPhonePermitMarketing () {
    return primaryPhonePermitMarketing;
  }

  public void setPrimaryPhonePermitMarketing ( boolean primaryPhonePermitMarketing ) {
    this.primaryPhonePermitMarketing = primaryPhonePermitMarketing;
  }

  public boolean isPreferredSupplier () {
    return preferredSupplier;
  }

  public void setPreferredSupplier ( boolean preferredSupplier ) {
    this.preferredSupplier = preferredSupplier;
  }

  public int getSupplierTravelCategoryFlags () {
    return supplierTravelCategoryFlags;
  }

  public void setSupplierTravelCategoryFlags ( int supplierTravelCategoryFlags ) {
    this.supplierTravelCategoryFlags = supplierTravelCategoryFlags;
  }

  public String getStreet1 () {
    return street1;
  }

  public void setStreet1 ( String street1 ) {
    this.street1 = street1;
  }

  public String getStreet2 () {
    return street2;
  }

  public void setStreet2 ( String street2 ) {
    this.street2 = street2;
  }

  public String getCity () {
    return city;
  }

  public void setCity ( String city ) {
    this.city = city;
  }

  public String getStateProvince () {
    return stateProvince;
  }

  public void setStateProvince ( String stateProvince ) {
    this.stateProvince = stateProvince;
  }

  public String getZipPostalCode () {
    return zipPostalCode;
  }

  public void setZipPostalCode ( String zipPostalCode ) {
    this.zipPostalCode = zipPostalCode;
  }

  public String getCountry () {
    return country;
  }

  public void setCountry ( String country ) {
    this.country = country;
  }

  public String getMailingStreet1 () {
    return mailingStreet1;
  }

  public void setMailingStreet1 ( String mailingStreet1 ) {
    this.mailingStreet1 = mailingStreet1;
  }

  public String getMailingStreet2 () {
    return mailingStreet2;
  }

  public void setMailingStreet2 ( String mailingStreet2 ) {
    this.mailingStreet2 = mailingStreet2;
  }

  public String getMailingCity () {
    return mailingCity;
  }

  public void setMailingCity ( String mailingCity ) {
    this.mailingCity = mailingCity;
  }

  public String getMailingStateProvince () {
    return mailingStateProvince;
  }

  public void setMailingStateProvince ( String mailingStateProvince ) {
    this.mailingStateProvince = mailingStateProvince;
  }

  public String getMailingZipPostalCode () {
    return mailingZipPostalCode;
  }

  public void setMailingZipPostalCode ( String mailingZipPostalCode ) {
    this.mailingZipPostalCode = mailingZipPostalCode;
  }

  public String getMailingCountry () {
    return mailingCountry;
  }

  public void setMailingCountry ( String mailingCountry ) {
    this.mailingCountry = mailingCountry;
  }

  public boolean isAddressPermitMarketing () {
    return addressPermitMarketing;
  }

  public void setAddressPermitMarketing ( boolean addressPermitMarketing ) {
    this.addressPermitMarketing = addressPermitMarketing;
  }

  public String getSupplierVendorId () {
    return supplierVendorId;
  }

  public void setSupplierVendorId ( String supplierVendorId ) {
    this.supplierVendorId = supplierVendorId;
  }

  public short getSupplierCommissionRate () {
    return supplierCommissionRate;
  }

  public void setSupplierCommissionRate ( short supplierCommissionRate ) {
    this.supplierCommissionRate = supplierCommissionRate;
  }

  public String getClientInformalSalutation () {
    return clientInformalSalutation;
  }

  public void setClientInformalSalutation ( String clientInformalSalutation ) {
    this.clientInformalSalutation = clientInformalSalutation;
  }

  public String getClientFormalSalutation () {
    return clientFormalSalutation;
  }

  public void setClientFormalSalutation ( String clientFormalSalutation ) {
    this.clientFormalSalutation = clientFormalSalutation;
  }

  public String getFirstId () {
    return firstId;
  }

  public void setFirstId ( String firstId ) {
    this.firstId = firstId;
  }

  public Communication.Type getProfileCommType () {
    return profileCommType;
  }

  public void setProfileCommType ( Communication.Type profileCommType ) {
    this.profileCommType = profileCommType;
  }

  public String getProfileCommValue () {
    return profileCommValue;
  }

  public void setProfileCommValue ( String profileCommValue ) {
    this.profileCommValue = profileCommValue;
  }

  public boolean isProfileCommIsBillingContact () {
    return profileCommIsBillingContact;
  }

  public void setProfileCommIsBillingContact ( boolean profileCommIsBillingContact ) {
    this.profileCommIsBillingContact = profileCommIsBillingContact;
  }
}