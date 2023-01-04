package com.trestechnologies.api.interfaces;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.http.client.methods.HttpUriRequest;

import java.io.Closeable;
import java.io.IOException;
import java.net.URL;

public interface APIContext extends Closeable {
  @FunctionalInterface
  interface Group<K> {
    void by ( K k ) throws IOException;
  }
  
  /**
   * Allows caller to add custom request headers.
   * 
   * <blockquote><pre>
   *   context.get("Version", r -> r.setHeader("User-Agent", "MyClient/1.0"))
   * </pre></blockquote>
   * 
   * Inspired by: <a href="https://docs.oracle.com/javase/8/docs/api/java/util/function/Consumer.html">Consumer</a>
   * 
   * @see java.util.function.Consumer
   */
  @FunctionalInterface
  interface RequestHeaders {
    void accept ( HttpUriRequest request );
    
    default void andThen ( HttpUriRequest request, String diagnostic ) {
      request.setHeader("Content-Type", "application/json");
      
      if ( diagnostic != null ) {
        request.setHeader("X-Tres-Diagnostic", diagnostic);
      }

      accept(request);
    }
  }

  URL getUrl ( );
  
  String getToken ( );

  String getApiVersion ( );

  /**
   * Logs into the Tres API.
   *
   * @see <a href="https://devportal.trestechnologies.com/api/Login.html">Login</a>
   * @param username Username of user or automatic process.
   * @param password Password of user or automatic process.
   * @param domain PCC or identifier of user ot automatic process.
   * @return <a href="https://devportal.trestechnologies.com/api/Login.html#example-success-response">result object</a>
   * @throws IOException
   */
  JsonNode login ( String username, String password, String domain ) throws IOException;

  /**
   * Logs out of the Tres API.
   *
   * @see <a href="https://devportal.trestechnologies.com/api/Logout.html">Logout</a>
   * @return <a href="https://devportal.trestechnologies.com/api/Logout.html#example-success-response">result object</a>
   * @throws IOException
   */
  JsonNode logout () throws IOException;

  /**
   * Refreshes the identity token of the Tres API.
   *
   * @see <a href="https://devportal.trestechnologies.com/search/?q=refreshIdentityToken">refreshIdentityToken</a>
   * @return result object
   * @throws IOException
   */
  JsonNode refreshIdentityToken () throws IOException;

  /**
   * Version of the Tres API.
   *
   * @see <a href="https://devportal.trestechnologies.com/api/Version.html">Version</a>
   * @return <a href="https://devportal.trestechnologies.com/api/Version.html#example-response">result object</a>
   * @throws IOException
   */
  JsonNode version () throws IOException;

  JsonNode getWithBasicAuth ( String method, String username, String password, RequestHeaders headers ) throws IOException;
  
  JsonNode getWithBasicAuth ( String method, String username, String password ) throws IOException;

  JsonNode get ( String method, RequestHeaders headers ) throws IOException;

  JsonNode get ( String method ) throws IOException;

  JsonNode post ( String method, RequestHeaders headers ) throws IOException;
  
  JsonNode post ( String method ) throws IOException;

  JsonNode post ( String method, Object object, RequestHeaders headers ) throws IOException;

  JsonNode post ( String method, Object object ) throws IOException;

  void batch ( Group<APIContext> group ) throws IOException;

  void close () throws IOException;
}
