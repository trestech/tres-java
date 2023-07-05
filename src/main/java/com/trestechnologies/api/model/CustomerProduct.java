package com.trestechnologies.api.model;

public class CustomerProduct {
  public enum Status {
    UNKNOWN("Unknown"), ACTIVE("Active"),
    PENDING("Pending"), CANCELLED("Cancelled");
    
    public final String name;
    
    Status ( String name ) {
      this.name = name;
    }
  }
  
  public enum Product {
    NONE(""), TBO("Trams Back Office"), CBW("ClientBase Windows"),
    CBO("ClientBase Online"), SYNC("Sync"), LIVE_CONNECT("LiveConnect"),
    TOUCH_BASE("TouchBase"), INTERBASE("InterBase"),
    CUSTOM_PROMOTIONS("CBMS Custom Promotions"), TRES_MBO("Tres MBO"),
    TBO_INVOICE_IMPORT_SPEC("TBO Invoice Import Spec"),
    TBO_PAYMENT_IMPORT_SPEC("TBO Payment Import Spec"),
    CONSORTIA_FEES("Consortia Fees"), API("API"), CC_MERCHANT("CC Merchant"),
    CCTE("CCTE"), MERGE_TO_PNR_SABRE("Merge to PNR Sabre"),
    PNR_IMPORT_SABRE("PNR Import Sabre");
    
    public final String name;
    
    Product ( String name ) {
      this.name = name;
    }
  }
}
