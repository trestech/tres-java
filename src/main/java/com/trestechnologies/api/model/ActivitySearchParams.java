package com.trestechnologies.api.model;

public class ActivitySearchParams extends BaseSearchModel {
  private NumSearchParam appUserRecNo;
  
//  private EnumSearchParam<Type> type

//  private DateSearchParam targetDateFrom;

//  private DateSearchParam targetDateTo

//  private DateTimeUTCSearchParam targetDateTimeFrom

//  private DateTimeUTCSearchParam targetDateTimeTo

  private Boolean completed;

  private NumSearchParam clientProfileRecNo;

  private NumSearchParam supplierProfileRecNo;

  private NumSearchParam personRecNo;

  private NumSearchParam tripRecNo;

  public NumSearchParam getAppUserRecNo () { return appUserRecNo; }

  public void setAppUserRecNo ( NumSearchParam appUserRecNo ) { this.appUserRecNo = appUserRecNo; }
  public void setAppUserRecNo ( long appUserRecNo ) { this.appUserRecNo = new NumSearchParam(appUserRecNo); }
  public void setAppUserRecNo ( NumSearchParam.Compare compareCondition, long appUserRecNo ) { this.appUserRecNo = new NumSearchParam(compareCondition, appUserRecNo); }
  public void setAppUserRecNo ( NumSearchParam.Compare compareCondition ) { this.appUserRecNo = new NumSearchParam(compareCondition); }

  public Boolean isCompleted () { return completed; }
  
  public void setCompleted ( Boolean completed ) { this.completed = completed; }

  public NumSearchParam getClientProfileRecNo () { return clientProfileRecNo; }

  public void setClientProfileRecNo ( NumSearchParam clientProfileRecNo ) { this.clientProfileRecNo = clientProfileRecNo; }
  public void setClientProfileRecNo ( long clientProfileRecNo ) { this.clientProfileRecNo = new NumSearchParam(clientProfileRecNo); }

  public NumSearchParam getSupplierProfileRecNo () { return supplierProfileRecNo; }

  public void setSupplierProfileRecNo ( NumSearchParam supplierProfileRecNo ) { this.supplierProfileRecNo = supplierProfileRecNo; }
  public void setSupplierProfileRecNo ( long supplierProfileRecNo ) { this.supplierProfileRecNo = new NumSearchParam(supplierProfileRecNo); }

  public NumSearchParam getPersonRecNo () { return personRecNo; }

  public void setPersonRecNo ( NumSearchParam personRecNo ) { this.personRecNo = personRecNo; }
  public void setPersonRecNo ( long personRecNo ) { this.personRecNo = new NumSearchParam(personRecNo); }

  public NumSearchParam getTripRecNo () { return tripRecNo; }

  public void setTripRecNo ( NumSearchParam tripRecNo ) { this.tripRecNo = tripRecNo; }
  public void setTripRecNo ( long tripRecNo ) { this.tripRecNo = new NumSearchParam(tripRecNo); }
}
