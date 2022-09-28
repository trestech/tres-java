package com.trestechnologies.api.model;

public class ProfileSearchParams extends BaseSearchModel {
  private Long profileType;

  private StringSearchParam profileName;

  private Long commType;

  private StringSearchParam commValue;

  private NumSearchParam clientAnniversaryMonth;

  private Long clientAnniversaryMonthFrom;

  private Long clientAnniversaryMonthTo;

  private Long clientAnniversaryDayFrom;

  private Long clientAnniversaryDayTo;

  private NumSearchParam clientAdvisorProfileRecNo;

  private Long clientType;

  private Long supplierType;

  private Boolean activestatus;

  private NumSearchParam clientBranchRecNo;

  private NumSearchParam personRecNo;

  private StringSearchParam personFirstName;

  private StringSearchParam personLastName;

  private Boolean preferredSupplier;

  private StringSearchParam street1;

  private StringSearchParam city;

  private StringSearchParam stateProvince;

  private StringSearchParam zipPostalCode;

  private StringSearchParam country;

  private MarketingSearchParam[] marketing;

  private StringSearchParam id;

  private Boolean emailPermitMarketing;

  private Boolean phonePermitMarketing;

  private Boolean addressPermitMarketing;

//  private DateTimeUTCSearchParam clientCreateDateTimeFrom;

//  private DateTimeUTCSearchParam clientCreateDateTimeTo;

//  private DateTimeUTCSearchParam clientModifiedDateTimeFrom;

//  private DateTimeUTCSearchParam clientModifiedDateTimeTo;

  private StringSearchParam supplierVendorId;

//  private TripSearchParams clientTripSearchParams;

//  private TripSearchParams supplierTripSearchParams;

//  private ActivitySearchParams clientActivitySearchParams;

  public Long getProfileType ( ) { return profileType; }

  public void setProfileType ( Long profileType ) { this.profileType = profileType; }
  public void setProfileType ( long profileType ) { this.profileType = profileType; }

  public StringSearchParam getProfileName ( ) { return profileName; }

  public void setProfileName ( StringSearchParam profileName ) { this.profileName = profileName; }
  public void setProfileName ( StringSearchParam.Compare compare, String profileName ) { this.profileName = new StringSearchParam(compare, profileName); }
  public void setProfileName ( StringSearchParam.Compare compare ) { this.profileName = new StringSearchParam(compare); }

  public Long getCommType ( ) { return commType; }

  public void setCommType ( Long commType ) { this.commType = commType; }
  public void setCommType ( long commType ) { this.commType = commType; }

  public StringSearchParam getCommValue ( ) { return commValue; }

  public void setCommValue ( StringSearchParam commValue ) { this.commValue = commValue; }

  public NumSearchParam getClientAnniversaryMonth ( ) { return clientAnniversaryMonth; }

  public void setClientAnniversaryMonth ( NumSearchParam clientAnniversaryMonth ) { this.clientAnniversaryMonth = clientAnniversaryMonth; }
  public void setClientAnniversaryMonth ( long clientAnniversaryMonth ) { this.clientAnniversaryMonth = new NumSearchParam(clientAnniversaryMonth); }

  public Long getClientAnniversaryMonthFrom ( ) { return clientAnniversaryMonthFrom; }

  public void setClientAnniversaryMonthFrom ( Long clientAnniversaryMonthFrom ) { this.clientAnniversaryMonthFrom = clientAnniversaryMonthFrom; }
  public void setClientAnniversaryMonthFrom ( long clientAnniversaryMonthFrom ) { this.clientAnniversaryMonthFrom = clientAnniversaryMonthFrom; }

  public Long getClientAnniversaryMonthTo ( ) { return clientAnniversaryMonthTo; }

  public void setClientAnniversaryMonthTo ( Long clientAnniversaryMonthTo ) { this.clientAnniversaryMonthTo = clientAnniversaryMonthTo; }
  public void setClientAnniversaryMonthTo ( long clientAnniversaryMonthTo ) { this.clientAnniversaryMonthTo = clientAnniversaryMonthTo; }

  public Long getClientAnniversaryDayFrom ( ) { return clientAnniversaryDayFrom; }

  public void setClientAnniversaryDayFrom ( Long clientAnniversaryDayFrom ) { this.clientAnniversaryDayFrom = clientAnniversaryDayFrom; }
  public void setClientAnniversaryDayFrom ( long clientAnniversaryDayFrom ) { this.clientAnniversaryDayFrom = clientAnniversaryDayFrom; }

  public Long getClientAnniversaryDayTo ( ) { return clientAnniversaryDayTo; }

  public void setClientAnniversaryDayTo ( Long clientAnniversaryDayTo ) { this.clientAnniversaryDayTo = clientAnniversaryDayTo; }
  public void setClientAnniversaryDayTo ( long clientAnniversaryDayTo ) { this.clientAnniversaryDayTo = clientAnniversaryDayTo; }

  public NumSearchParam getClientAdvisorProfileRecNo ( ) { return clientAdvisorProfileRecNo; }

  public void setClientAdvisorProfileRecNo ( NumSearchParam clientAdvisorProfileRecNo ) { this.clientAdvisorProfileRecNo = clientAdvisorProfileRecNo; }
  public void setClientAdvisorProfileRecNo ( long clientAdvisorProfileRecNo ) { this.clientAdvisorProfileRecNo = new NumSearchParam(clientAdvisorProfileRecNo); }

  public Long getClientType ( ) { return clientType; }

  public void setClientType ( Long clientType ) { this.clientType = clientType; }
  public void setClientType ( long clientType ) { this.clientType = clientType; }

  public Long getSupplierType ( ) { return supplierType; }

  public void setSupplierType ( Long supplierType ) { this.supplierType = supplierType; }
  public void setSupplierType ( long supplierType ) { this.supplierType = supplierType; }

  public Boolean isActivestatus ( ) { return activestatus; }

  public void setActivestatus ( Boolean activestatus ) { this.activestatus = activestatus; }

  public NumSearchParam getClientBranchRecNo ( ) { return clientBranchRecNo; }

  public void setClientBranchRecNo ( NumSearchParam clientBranchRecNo ) { this.clientBranchRecNo = clientBranchRecNo; }
  public void setClientBranchRecNo ( long clientBranchRecNo ) { this.clientBranchRecNo = new NumSearchParam(clientBranchRecNo); }

  public NumSearchParam getPersonRecNo ( ) { return personRecNo; }

  public void setPersonRecNo ( NumSearchParam personRecNo ) { this.personRecNo = personRecNo; }
  public void setPersonRecNo ( long personRecNo ) { this.personRecNo = new NumSearchParam(personRecNo); }

  public StringSearchParam getPersonFirstName ( ) { return personFirstName; }

  public void setPersonFirstName ( StringSearchParam personFirstName ) { this.personFirstName = personFirstName; }

  public StringSearchParam getPersonLastName ( ) { return personLastName; }

  public void setPersonLastName ( StringSearchParam personLastName ) { this.personLastName = personLastName; }

  public Boolean isPreferredSupplier ( ) { return preferredSupplier; }

  public void setPreferredSupplier ( Boolean preferredSupplier ) { this.preferredSupplier = preferredSupplier; }

  public StringSearchParam getStreet1 ( ) { return street1; }

  public void setStreet1 ( StringSearchParam street1 ) { this.street1 = street1; }

  public StringSearchParam getCity ( ) { return city; }

  public void setCity ( StringSearchParam city ) { this.city = city; }

  public StringSearchParam getStateProvince ( ) { return stateProvince; }

  public void setStateProvince ( StringSearchParam stateProvince ) { this.stateProvince = stateProvince; }

  public StringSearchParam getZipPostalCode ( ) { return zipPostalCode; }

  public void setZipPostalCode ( StringSearchParam zipPostalCode ) { this.zipPostalCode = zipPostalCode; }

  public StringSearchParam getCountry ( ) { return country; }

  public void setCountry ( StringSearchParam country ) { this.country = country; }

  public MarketingSearchParam[] getMarketing ( ) { return marketing; }

  public void setMarketing ( MarketingSearchParam[] marketing ) { this.marketing = marketing; }

  public StringSearchParam getId ( ) { return id; }

  public void setId ( StringSearchParam id ) { this.id = id; }

  public Boolean isEmailPermitMarketing ( ) { return emailPermitMarketing; }

  public void setEmailPermitMarketing ( Boolean emailPermitMarketing ) { this.emailPermitMarketing = emailPermitMarketing; }

  public Boolean isPhonePermitMarketing ( ) { return phonePermitMarketing; }

  public void setPhonePermitMarketing ( Boolean phonePermitMarketing ) { this.phonePermitMarketing = phonePermitMarketing; }

  public Boolean isAddressPermitMarketing ( ) { return addressPermitMarketing; }

  public void setAddressPermitMarketing ( Boolean addressPermitMarketing ) { this.addressPermitMarketing = addressPermitMarketing; }

  public StringSearchParam getSupplierVendorId ( ) { return supplierVendorId; }

  public void setSupplierVendorId ( StringSearchParam supplierVendorId ) { this.supplierVendorId = supplierVendorId; }
}
