package com.trestechnologies.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trestechnologies.api.interfaces.APIContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import static com.trestechnologies.api.Methods.*;
import static com.trestechnologies.api.TresContext.DEVELOP_URL;
import static java.lang.System.err;
import static java.lang.System.out;

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
  protected String url, username, password, domain, token, fullCommand;
  
  protected StringBuilder in;
  
  protected APIContext context;
  
  protected CommandLine ( ) throws IOException {
    Map<String, String> env = System.getenv();
    
    url = env.get("TRES_URL");
    username = env.get("TRES_USERNAME");
    password = env.get("TRES_PASSWORD");
    domain = env.get("TRES_DOMAIN");
    token = env.get("TRES_TOKEN");
    in = new StringBuilder();
    fullCommand = System.getProperty("sun.java.command");
    
    try ( BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)) ) {
      if ( reader.ready() ) {
        String line;

        while ( (line = reader.readLine()) != null ) {
          in.append(line).append("\n");
        }
      }
    }

    if ( url == null || url.isEmpty() ) {
      out.println("Environment variable not set: TRES_URL");
      out.println("E.g.: TRES_URL=" + DEVELOP_URL);

      System.exit(2);
    } else if ( ( username == null || password == null || domain == null ) && token == null ) {
      out.println("One or more environment variables not set: TRES_USERNAME, TRES_PASSWORD, TRES_DOMAIN, TRES_TOKEN");

      System.exit(2);
    }
  }
  
  public CommandLine ( APIContext context ) {
    this.context = context;
  }
  
  protected String bestCommand ( String command ) {
    String bestCommand;
    
    if ( fullCommand.contains("exec:java") ) {
      bestCommand = command;
    } else if ( fullCommand.contains(".jar") ) {
      bestCommand = "java -jar " + fullCommand;
    } else {
      bestCommand = "java -jar tres-java.jar";
    }

    return bestCommand;
  }
  
  protected void refreshToken ( ) {
    if ( token == null || token.isEmpty() ) {
      try ( TresContext ctx = new TresContext(url, username, password, domain) ) {
        ctx.batch(c -> token = c.getToken());
      } catch ( Exception e ) {
        err.println("ERROR: " + e.getMessage());
        System.exit(-1);
      }
    } else {
      try ( TresContext ctx = new TresContext(url, token) ) {
        JsonNode result = ctx.refreshIdentityToken();
        String newToken = result.get("refreshIdentityToken").asText();
        
        assert newToken != null && !newToken.isEmpty() : "No token returned";
        
        if ( !newToken.equals(token) ) {
          err.println("WARNING: Unexpected token change");
        }
        
        token = newToken;
      } catch ( Exception e ) {
        err.println("ERROR: " + e.getMessage());
        System.exit(-1);
      }
    }
  }
  
  public static void main ( String[] args ) throws IOException {
    CommandLine cmd = new CommandLine();
    
    if ( args.length == 0 ) {
      String command = cmd.bestCommand("./bin/tres.sh");
      
      out.println("Usage: " + command + " <method>");
      out.println("\nMethods:");
      out.println("\t" + VERSION);
      out.println("\t" + LOGIN);
      out.println("\t" + LOGOUT);
      out.println("\t" + REFRESH_IDENTITY_TOKEN);
      out.println("\nAlso see: " + cmd.url + "/swagger");
      out.println("          https://devportal.trestechnologies.com/api/");
      out.println("\nExample POST:\n\necho '{\"topRows\": 5, \"includeCols\": [\"name\", \"primaryEmail\"]}' | " + command + " ProfileSearch");
      out.println("echo '{\"topRows\": 5}' | " + command + " TagSearch");
      
      System.exit(1);
    }
    
    cmd.refreshToken();
    
    try ( TresContext ctx = new TresContext(cmd.url, cmd.token) ) {
      ctx.batch(c -> {
        String method = args[0];
        JsonNode result;

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

        out.println(result.toPrettyString());
      });
    }
  }
}
