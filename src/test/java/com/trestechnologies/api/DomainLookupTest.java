package com.trestechnologies.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.trestechnologies.api.annotation.ExitOnError;
import com.trestechnologies.api.model.CustomerProduct;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ExitOnError
public class DomainLookupTest extends BaseTestCase {
  public void testQueryCustomerProfiles ( ) { ((WithMockAdminWebServer) context -> {
    DomainLookup domainLookup = new DomainLookup(context);
    List<String> trinIds = Collections.singletonList("57052");
    JsonNode customerProfiles = domainLookup.queryCustomerProfiles(trinIds);
    
    assert !customerProfiles.isEmpty() : "customer profiles is empty";
  }).group("domain_lookup_query_customer_profiles"); }
  
  public void testQueryCustomerProducts ( ) { ((WithMockAdminWebServer) context -> {
    DomainLookup domainLookup = new DomainLookup(context);
    List<String> trinIds = Collections.singletonList("57052");
    JsonNode customerProfiles = domainLookup.queryCustomerProfiles(trinIds);

    if ( !customerProfiles.isEmpty() ) {
      JsonNode customerProducts = domainLookup.queryCustomerProducts(customerProfiles);

      assert !customerProducts.isEmpty() : "customer products is empty";
    }
  }).group("domain_lookup_query_customer_products"); }

  public void testQueryCustomerProductsOnlyTBOCBO ( ) { ((WithMockAdminWebServer) context -> {
    DomainLookup domainLookup = new DomainLookup(context);
    JsonNode customerProfiles = domainLookup.queryCustomerProfiles(null);

    if ( !customerProfiles.isEmpty() ) {
      List<CustomerProduct.Product> products = Arrays.asList(CustomerProduct.Product.TBO, CustomerProduct.Product.CBO);
      JsonNode customerProducts = domainLookup.queryCustomerProducts(customerProfiles, products);

      assert !customerProducts.isEmpty() : "customer products is empty";
      
      customerProducts.forEach(product -> {
        int productRecNo = product.get("product_recNo").intValue();
        
        assert productRecNo == CustomerProduct.Product.TBO.ordinal() || productRecNo == CustomerProduct.Product.CBO.ordinal() : "product is not TBO or CBO: " + productRecNo;
      });
    }
  }).group("domain_lookup_query_customer_products_only_tbo_cbo"); }
}
