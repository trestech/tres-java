package com.trestechnologies.api.interfaces;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.net.URL;

public class APIContextAdapter implements APIContext {
  @Override
  public URL getUrl () { return null; }

  @Override
  public String getToken () { return null; }
  
  @Override
  public String getApiVersion () { return null; }
  
  @Override
  public JsonNode login ( String username, String password, String domain ) throws IOException {
    return null;
  }
  
  @Override
  public JsonNode logout () throws IOException {
    return null;
  }
  
  @Override
  public JsonNode refreshIdentityToken () throws IOException {
    return null;
  }
  
  @Override
  public JsonNode version () throws IOException {
    return null;
  }

  @Override
  public JsonNode getWithBasicAuth ( String method, String username, String password, RequestHeaders headers ) throws IOException {
    return null;
  }

  @Override
  public JsonNode getWithBasicAuth ( String method, String username, String password ) throws IOException {
    return null;
  }

  @Override
  public JsonNode get ( String method, RequestHeaders headers ) throws IOException {
    return null;
  }

  @Override
  public JsonNode get ( String method ) throws IOException {
    return null;
  }

  @Override
  public JsonNode post ( String method, RequestHeaders headers ) throws IOException {
    return post(method, null);
  }

  @Override
  public JsonNode post ( String method ) throws IOException {
    return post(method, null, null);
  }

  @Override
  public JsonNode post ( String method, Object object, RequestHeaders headers ) throws IOException {
    return null;
  }

  @Override
  public JsonNode post ( String method, Object object ) throws IOException {
    return null;
  }

  @Override
  public void batch ( Group<APIContext> group ) throws IOException { }

  @Override
  public void close () throws IOException { }
}
