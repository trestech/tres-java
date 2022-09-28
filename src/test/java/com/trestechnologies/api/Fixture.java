package com.trestechnologies.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import kotlin.Pair;
import okhttp3.mockwebserver.RecordedRequest;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties({"liveUrl", "requestPath"})
public class Fixture {
  private static final String FIXTURES_PATH = "src/test/fixtures";
  
  private static final YAMLFactory FACTORY = new YAMLFactory().
    disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER).
    enable(YAMLGenerator.Feature.LITERAL_BLOCK_STYLE).
    enable(YAMLGenerator.Feature.MINIMIZE_QUOTES);

  private static final ObjectMapper MAPPER = new ObjectMapper(FACTORY);

  private String requestPath;

  // Fields saved to YAML are as follows:

  private String diagnostic;
  private String url;
  private String method;
  private String requestBody;
  private String body;
  private String status;
  private Map<String, String> requestHeaders = new HashMap<>();
  private Map<String, String> responseHeaders = new HashMap<>();

  public static Fixture read ( RecordedRequest request ) throws IOException {
    String method = request.getMethod(); assert method != null;
    String requestPath = request.getPath(); assert requestPath != null;
    String diagnostic = extractDiagnostic(request);
    Fixture fixture;
    String filePath = filePath(method, requestPath, diagnostic);
    File file = new File(filePath);

    if ( file.isFile() && file.exists() ) {
      fixture = MAPPER.readValue(file, Fixture.class);
    } else {
      fixture = new Fixture();

      fixture.setDiagnostic(diagnostic);
      fixture.setMethod(method);
    }
    
    fixture.setRequestPath(requestPath);

    return fixture;
  }

  private static String extractDiagnostic ( RecordedRequest request ) {
    for ( Pair<? extends String, ? extends String> header : request.getHeaders() ) {
      if ( header.getFirst().equalsIgnoreCase("X-Tres-Diagnostic") ) {
        return header.getSecond();
      }
    }
    
    return null;
  }

  public static void write ( Fixture fixture ) throws IOException {
    String filePath = filePath(fixture.getMethod(), fixture.getRequestPath(), fixture.getDiagnostic());
    File file = new File(filePath);

    MAPPER.writeValue(file, fixture);
  }

  private static String filePath ( String method, String requestPath, String diagnostic ) {
    String retVal = method + requestPath.replaceAll("[^a-zA-Z]+", "_");

    retVal = retVal.replaceAll("[_]+$", "");

    if ( diagnostic != null && diagnostic.length() > 0 ) {
      retVal = diagnostic + "_" + retVal;
    }
    
    if ( !new File(FIXTURES_PATH).exists() ) {
      throw new RuntimeException("Fixtures path missing: " + FIXTURES_PATH);
    }
    
    return FIXTURES_PATH + File.separator + retVal + ".yml";
  }

  public String getRequestPath () { return requestPath; }
  public void setRequestPath ( String requestPath ) { this.requestPath = requestPath; }
  
  // Properties saved to YAML are as follows:

  public String getDiagnostic () { return diagnostic; }
  public void setDiagnostic ( String diagnostic ) { this.diagnostic = diagnostic; }
  public String getUrl () { return url; }
  public void setUrl ( String url ) { this.url = url; }
  public String getMethod () { return method; }
  public void setMethod ( String method ) { this.method = method; }
  public String getRequestBody () { return requestBody; }
  public void setRequestBody ( String requestBody ) { this.requestBody = requestBody; }
  public String getBody () { return body; }
  public void setBody ( String body ) { this.body = body; }
  public String getStatus () { return status; }
  public void setStatus ( String status ) { this.status = status; }
  public Map<String, String> getRequestHeaders () { return requestHeaders; }
  public void setRequestHeaders ( Map<String, String> requestHeaders ) { this.requestHeaders = requestHeaders; }
  public Map<String, String> getResponseHeaders () { return responseHeaders; }
  public void setResponseHeaders ( Map<String, String> responseHeaders ) { this.responseHeaders = responseHeaders; }
}
