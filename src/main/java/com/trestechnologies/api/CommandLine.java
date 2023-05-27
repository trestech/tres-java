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
 * Command line interface for Tres API.  This class is used to test the API and
 * is not intended for production use.
 * 
 * <p>Environment variables:</p>
 * <ul>
 *   <li>TRES_URL - URL of Tres API</li>
 *   <li>TRES_USERNAME - Username for Tres API</li>
 *   <li>TRES_PASSWORD - Password for Tres API</li>
 *   <li>TRES_DOMAIN - Domain for Tres API</li>
 *   <li>TRES_TOKEN - Token for Tres API</li>
 *   <li>TRES_DEBUG - Debug flag for Tres API</li>
 *   <li>TRES_TIMEOUT - Timeout for Tres API</li>
 *   <li>TRES_VERSION - Version for Tres API</li>
 * </ul>
 */
public class CommandLine {
  public static void main ( String[] args ) throws IOException {
    Map<String, String> env = System.getenv();
    String url = env.get("TRES_URL");
    String username = env.get("TRES_USERNAME");
    String password = env.get("TRES_PASSWORD");
    String domain = env.get("TRES_DOMAIN");
    AtomicReference<String> token = new AtomicReference<>(env.get("TRES_TOKEN"));
    StringBuilder in = new StringBuilder(); 
    String fullCommand = System.getProperty("sun.java.command"); 
    
    try ( BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)) ) {
      if ( reader.ready() ) {
        String line;
        
        while ( (line = reader.readLine()) != null ) {
          in.append(line).append("\n");
        }
      }
    }
    
    if ( url == null || url.isEmpty() ) {
      System.out.println("Environment variable not set: TRES_URL");
      System.out.println("E.g.: TRES_URL=" + DEVELOP_URL);

      System.exit(2);
    } else if ( ( username == null || password == null || domain == null ) && token.get() == null ) {
      System.out.println("One or more environment variables not set: TRES_USERNAME, TRES_PASSWORD, TRES_DOMAIN, TRES_TOKEN");

      System.exit(2);
    }
    
    assert args.length < 2 : "Too many arguments";
    
    if ( args.length == 0 ) {
      String cmd;
      
      if ( fullCommand.contains("exec:java") ) {
        cmd = "./bin/tres.sh";
      } else if ( fullCommand.contains(".jar") ) {
        cmd = "java -jar " + fullCommand;
      } else {
        cmd = "java -jar tres-java.jar";
      }

      System.out.println("Usage: " + cmd + " <method>");
      System.out.println("\nMethods:");
      System.out.println("\t" + VERSION);
      System.out.println("\t" + LOGIN);
      System.out.println("\t" + LOGOUT);
      System.out.println("\t" + REFRESH_IDENTITY_TOKEN);
      System.out.println("\nAlso see: " + url + "/swagger");
      System.out.println("          https://devportal.trestechnologies.com/api/");
      System.out.println("\nExample POST:\n\necho '{\"topRows\": 5, \"includeCols\": [\"name\", \"primaryEmail\"]}' | " + cmd + " ProfileSearch");
      System.out.println("echo '{\"topRows\": 5}' | " + cmd + " TagSearch");
      
      System.exit(1);
    }
    
    if ( token.get() == null || token.get().isEmpty() ) {
      try ( TresContext ctx = new TresContext(url, username, password, domain) ) {
        ctx.batch(c -> token.set(c.getToken()));
      } catch ( Exception e ) {
        System.err.println("Error: " + e.getMessage());
      }
    }
    
    try ( TresContext ctx = new TresContext(url, token.get()) ) {
      ctx.batch(c -> {
        String method = args[0];
        JsonNode result;

        if ( in.length() == 0 ) {
          // No pipe passed to method.  For these, we pick up any arguments from
          // environment variables.

          if ( VERSION.equalsIgnoreCase(method) ) result = c.version();
          else if ( LOGIN.equalsIgnoreCase(method) ) result = c.login(username, password, domain);
          else if ( LOGOUT.equalsIgnoreCase(method) ) result = c.logout();
          else if ( REFRESH_IDENTITY_TOKEN.equalsIgnoreCase(method) ) result = c.refreshIdentityToken();
          else result = c.get(method);
        } else {
          // Pipe passed to method call is assumed to be JSON.

          ObjectMapper mapper = new ObjectMapper();
          JsonNode params = mapper.readTree(new String(in));

          result = c.post(method, params);
        }

        System.out.println(result.toPrettyString());
      });
    }
  }
}
