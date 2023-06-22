package com.trestechnologies.api.model;

public class CustomerProduct {
  public enum Status { UNKNOWN, ACTIVE, PENDING, CANCELLED }
  
  public enum Product {
    NONE, TBO, CBW, CBO, SYNC, LIVE_CONNECT, TOUCH_BASE, INTERBASE,
    CUSTOM_PROMOTIONS, TRES_MBO, TBO_INVOICE_IMPORT_SPEC,
    TBO_PAYMENT_IMPORT_SPEC, CONSORTIA_FEES, API, CC_MERCHANT, CCTE,
    MERGE_TO_PNR_SABRE, PNR_IMPORT_SABRE
  }
}
