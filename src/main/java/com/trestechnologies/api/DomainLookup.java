package com.trestechnologies.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.trestechnologies.api.interfaces.APIContext;
import com.trestechnologies.api.model.CustomerProduct;
import com.trestechnologies.api.model.StringSearchParam;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.System.out;

/**
 * Export product records for a given Trin ID to stdout.
 */
public class DomainLookup extends CommandLine {
  private static final String[] PROFILE_COLS = {
    "recNo",
    "tramsId",
  };
  
  private static final String[] PRODUCT_COLS = {
    "recNo",
    "customerProfile_recNo",
    "product_recNo",
    "serialNumber",
    "status",
    "cboAlias",
  };
  
  protected DomainLookup () throws IOException {
    super();
  }
  
  public DomainLookup ( APIContext context ) {
    super(context);
  }

  public JsonNode queryCustomerProfiles ( List<String> trinIds ) throws IOException {
    return queryCustomerProfiles(context, trinIds);
  }
  
  public JsonNode queryCustomerProfiles ( APIContext ctx, List<String> trinIds ) throws IOException {
    ObjectNode params = JsonNodeFactory.instance.objectNode();
    ArrayNode includeCols = params.putArray("includeCols");
    ObjectNode tramsId = params.putObject("tramsId");
    ArrayNode tramsIdValues = tramsId.putArray("value");

    params.put("startingRow", 0);
    
    for ( String col : PROFILE_COLS ) {
      includeCols.add(col);
    }
    
    if ( trinIds != null ) {
      trinIds.forEach(tramsIdValues::add);
    }

    return ctx.post("CustomerProfileSearch", params);
  }

  public JsonNode queryCustomerProducts ( JsonNode customerProfiles ) throws IOException {
    return queryCustomerProducts(context, customerProfiles);
  }

  public JsonNode queryCustomerProducts ( JsonNode customerProfiles, List<CustomerProduct.Product> products ) throws IOException {
    return queryCustomerProducts(context, customerProfiles, products);
  }

  public JsonNode queryCustomerProducts ( APIContext ctx, JsonNode customerProfiles ) throws IOException {
    return queryCustomerProducts(ctx, customerProfiles, null);
  }
  
  public JsonNode queryCustomerProducts ( APIContext ctx, JsonNode customerProfiles, List<CustomerProduct.Product> products ) throws IOException {
    ObjectNode productParams = JsonNodeFactory.instance.objectNode();
    ArrayNode productIncludeCols = productParams.putArray("includeCols");
    ArrayNode customerProfileRecNos = productParams.putObject("CustomerProfile_recNo").putArray("value");
    ArrayNode statuses = productParams.putObject("status").putArray("value");
    JsonNode customerProducts;

    productParams.put("startingRow", 0);
    
    for ( String col : PRODUCT_COLS ) {
      productIncludeCols.add(col);
    }
    
    customerProfiles.forEach(customer -> customerProfileRecNos.add(customer.get("recNo")));
    
    if ( products != null && !products.isEmpty() ) {
      ArrayNode productRecNos = productParams.putObject("product_recNo").putArray("value");
      
      products.forEach(product -> productRecNos.add(product.ordinal()));
    }
    
    statuses.add(CustomerProduct.Status.ACTIVE.ordinal());
    
    customerProducts = ctx.post("CustomerProductSearch", productParams);
    
    // Now we mutate the result because the API doesn't support these joins or
    // certain filters.
    
    for ( Iterator<JsonNode> i = customerProducts.iterator(); i.hasNext(); ) {
      JsonNode customerProduct = i.next();
      JsonNode cboAlias = customerProduct.get("cboAlias");
      
      // Joins
      
      customerProfiles.forEach(customer -> {
        if ( customerProduct.get("customerProfile_recNo").asLong() == customer.get("recNo").asLong() ) {
          ((ObjectNode) customerProduct).put("tramsId", customer.get("tramsId").asLong());
        }
      });
      
      // Filters
      
      if ( cboAlias == null || cboAlias.asText().isEmpty() ) {
        i.remove();
      }
    }
    
    return customerProducts;
  }
  
  public static void main ( String[] args ) throws IOException {
    DomainLookup cmd = new DomainLookup();
    
//    if ( args.length == 0 ) {
//      String command = cmd.bestCommand("./bin/domain-lookup.sh");
//
//      out.println("Usage: " + command + " [Trin ID]");
//
//      System.exit(1);
//    }
    
    cmd.refreshToken();
    
    try ( TresContext ctx = new TresContext(cmd.url, cmd.token) ) {
      JsonNode customerProfiles = cmd.queryCustomerProfiles(ctx, Arrays.asList(args));

      // out.println("Looking up domains for CustomerProfile: " + customerProfiles);
      // out.println(customerProducts.toPrettyString());
      
      for ( String col : PRODUCT_COLS ) {
        if ( col.equals("product_recNo") ) {
          out.print("productName\t");
        } else {
          out.print(col + "\t");
        }
      }
      
      out.print("tramsId");
      out.print("\n");
      
      if ( !customerProfiles.isEmpty() ) {
        List<CustomerProduct.Product> products = Collections.singletonList(CustomerProduct.Product.TRES_MBO); // TODO use TRES_MBO instead of TBO
        JsonNode customerProducts = cmd.queryCustomerProducts(ctx, customerProfiles, products);

        customerProducts.forEach(( JsonNode customerProduct ) -> {
          for ( String col : PRODUCT_COLS ) {
            out.print(extractKey(customerProduct, col) + "\t");
          }
          
          out.println(extractKey(customerProduct, "tramsId"));
        });
      }
    }
  }
  
  private static String extractKey ( JsonNode node, String key ) {
    JsonNode value = node.get(key);
    
    if ( value == null || value.isNull() ) { return ""; }

    if ( key.equals("product_recNo") ) {
      return CustomerProduct.Product.values()[value.intValue()].name;
    } else if ( key.equals("status") ) {
      return CustomerProduct.Status.values()[value.intValue()].name;
    } else {
      return value.asText();
    }
  }
}
