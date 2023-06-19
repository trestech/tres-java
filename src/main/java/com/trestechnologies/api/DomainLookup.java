package com.trestechnologies.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.trestechnologies.api.interfaces.APIContext;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Export product records for a given Trin ID to stdout.
 */
public class DomainLookup extends CommandLine {
  protected DomainLookup () throws IOException {
    super();
  }
  
  public DomainLookup ( APIContext context ) {
    super(context);
  }

  public JsonNode queryCustomerProfiles ( String[] trinIds ) throws IOException {
    return queryCustomerProfiles(context, trinIds);
  }
  
  public JsonNode queryCustomerProfiles ( APIContext ctx, String[] trinIds ) throws IOException {
    ObjectNode params = JsonNodeFactory.instance.objectNode();
    ArrayNode includeCols = params.putArray("includeCols");
    ObjectNode tramsId = params.putObject("tramsId");
    ArrayNode tramsIdValues = tramsId.putArray("value");

    params.put("startingRow", 0);
    includeCols.add("recNo");
    includeCols.add("tramsId");

    for ( String trinId : trinIds ) {
      tramsIdValues.add(trinId);
    }

    return ctx.post("CustomerProfileSearch", params);
  }

  public JsonNode queryCustomerProducts ( JsonNode customerProfiles ) throws IOException {
    return queryCustomerProducts(context, customerProfiles);
  }
  
  public JsonNode queryCustomerProducts ( APIContext ctx, JsonNode customerProfiles ) throws IOException {
    ObjectNode productParams = JsonNodeFactory.instance.objectNode();
    ArrayNode productIncludeCols = productParams.putArray("includeCols");
    ObjectNode customerProfileRecNo = productParams.putObject("CustomerProfile_recNo");
    ArrayNode customerProfileRecNos = customerProfileRecNo.putArray("value");
    JsonNode customerProducts;

    productParams.put("startingRow", 0);
    productIncludeCols.add("recNo");
    productIncludeCols.add("customerProfile_recNo");
    productIncludeCols.add("productName");
    productIncludeCols.add("serialNumber");
    productIncludeCols.add("status");
    productIncludeCols.add("cboAlias");

    customerProfiles.forEach(( JsonNode customer ) -> {
      customerProfileRecNos.add(customer.get("recNo"));
    });
    
    // TODO Add product id filter
    
    customerProducts = ctx.post("CustomerProductSearch", productParams);
    
    customerProducts.forEach(( JsonNode customerProduct ) -> {
      customerProfiles.forEach(( JsonNode customer ) -> {
        if ( customerProduct.get("customerProfile_recNo").asLong() == customer.get("recNo").asLong() ) {
          ((ObjectNode) customerProduct).put("tramsId", customer.get("tramsId").asLong());
        }
      });
    });
    
    return customerProducts;
  }
  
  public static void main ( String[] args ) throws IOException {
    DomainLookup cmd = new DomainLookup();
    
//    if ( args.length == 0 ) {
//      String command = cmd.bestCommand("./bin/domain-lookup.sh");
//
//      System.out.println("Usage: " + command + " [Trin ID]");
//
//      System.exit(1);
//    }
    
    cmd.refreshToken();
    
    try ( TresContext ctx = new TresContext(cmd.url, cmd.token) ) {
      JsonNode customerProfiles = cmd.queryCustomerProfiles(ctx, args);

      // System.out.println("Looking up domains for CustomerProfile: " + customerProfiles);
      // System.out.println(customerProducts.toPrettyString());
      
      System.out.println("customerProduct_recNo\tcustomerProfile_recNo\tproductName\tserialNumber\tstatus\tcboAlias\ttramsId");
      
      if ( !customerProfiles.isEmpty() ) {
        JsonNode customerProducts = cmd.queryCustomerProducts(ctx, customerProfiles);

        customerProducts.forEach(( JsonNode customerProduct ) -> {
          System.out.print(extractKey(customerProduct, "recNo") + "\t");
          System.out.print(extractKey(customerProduct, "customerProfile_recNo") + "\t");
          System.out.print(extractKey(customerProduct, "productName") + "\t");
          System.out.print(extractKey(customerProduct, "serialNumber") + "\t");
          System.out.print(extractKey(customerProduct, "status") + "\t");
          System.out.print(extractKey(customerProduct, "cboAlias") + "\t");
          System.out.println(extractKey(customerProduct, "tramsId"));
        });
      }
    }
  }
  
  private static String extractKey ( JsonNode node, String key ) {
    JsonNode value = node.get(key);
    
    if ( value == null || value.isNull() ) { return ""; }
    
    return value.asText();
  }
}
