package com.trestechnologies.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static com.trestechnologies.api.Methods.*;
import static com.trestechnologies.api.TresContext.DEVELOP_URL;

/**
 * Dumps the Tres API schema for a method to stdout.
 */
public class DumpSchema extends CommandLine {
  protected DumpSchema () throws IOException {
    super();
  }

  public static void main ( String[] args ) throws IOException {
    CommandLine cmd = new DumpSchema();
    
    assert args.length < 2 : "Too many arguments";
    
    if ( args.length == 0 ) {
      String command = cmd.bestCommand("./bin/tres.sh");
      
      System.out.println("Usage: " + command + " <method>");
      System.out.println("\nMethods:");
      System.out.println("\t" + VERSION);
      System.out.println("\t" + LOGIN);
      System.out.println("\t" + LOGOUT);
      System.out.println("\t" + REFRESH_IDENTITY_TOKEN);
      System.out.println("\nAlso see: " + cmd.url + "/swagger");
      System.out.println("          https://devportal.trestechnologies.com/api/");
      System.out.println("\nExample POST:\n\necho '{\"topRows\": 5, \"includeCols\": [\"name\", \"primaryEmail\"]}' | " + command + " ProfileSearch");
      System.out.println("echo '{\"topRows\": 5}' | " + command + " TagSearch");
      
      System.exit(1);
    }
    
    cmd.refreshToken();
    
    try ( TresContext ctx = new TresContext(cmd.url, cmd.token) ) {
      ctx.batch(c -> {
        String method = args[0];
        JsonNode result, sample;

        if ( cmd.in.length() == 0 ) {
          // No pipe passed to method.  For these, we pick up any arguments from
          // environment variables.

          if ( VERSION.equalsIgnoreCase(method) ) result = c.version();
          else if ( LOGIN.equalsIgnoreCase(method) ) result = c.login(cmd.username, cmd.password, cmd.domain);
          else if ( LOGOUT.equalsIgnoreCase(method) ) result = c.logout();
          else if ( REFRESH_IDENTITY_TOKEN.equalsIgnoreCase(method) ) result = c.refreshIdentityToken();
          else result = c.get(method);
        } else {
          // Pipe passed to method call is assumed to be JSON.

          ObjectMapper mapper = new ObjectMapper();
          JsonNode params = mapper.readTree(new String(cmd.in));

          result = c.post(method, params);
        }
        
        if ( result.isObject() ) {
          sample = result;
        } else if ( result.size() > 0 ) {
          sample = result.get(0);
        } else {
          sample = null;
          System.err.println("No results returned");

          System.exit(1);
        }
        
        sample.fields().forEachRemaining(e -> {
          System.out.println(e.getValue().getNodeType() + " " + e.getKey());
        });
      });
    }
  }
}
