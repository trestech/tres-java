package com.trestechnologies.api;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.concurrent.atomic.AtomicBoolean;

public class DomainLookupTest extends BaseTestCase {
  public void testQueryCustomerProfiles ( ) { ((WithMockAdminWebServer) context -> {
    DomainLookup domainLookup = new DomainLookup(context);
    String [] trinIds = new String[] {"57052"};
    JsonNode customerProfiles = domainLookup.queryCustomerProfiles(trinIds);
    
    assert !customerProfiles.isEmpty() : "customer profiles is empty";
  }).group("domain_lookup_query_customer_profiles"); }
  
  public void testQueryCustomerProducts ( ) { ((WithMockAdminWebServer) context -> {
    DomainLookup domainLookup = new DomainLookup(context);
    String [] trinIds = new String[] {"57052"};
    JsonNode customerProfiles = domainLookup.queryCustomerProfiles(trinIds);

    if ( !customerProfiles.isEmpty() ) {
      JsonNode customerProducts = domainLookup.queryCustomerProducts(customerProfiles);

      assert !customerProducts.isEmpty() : "customer products is empty";
    }
  }).group("domain_lookup_query_customer_products"); }
}
