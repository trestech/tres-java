package com.trestechnologies.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import kotlin.Pair;
import okhttp3.mockwebserver.RecordedRequest;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
  private String paramsHash;
  private String requestBody;
  private String body;
  private String status;
  private Map<String, String> requestHeaders = new HashMap<>();
  private Map<String, String> responseHeaders = new HashMap<>();

  public static Fixture read ( RecordedRequest request ) throws IOException, NoSuchAlgorithmException {
    String method = request.getMethod(); assert method != null;
    String requestPath = request.getPath(); assert requestPath != null;
    String diagnostic = extractDiagnostic(request);
    String requestBody = request.getBody().readUtf8();
    String paramsHash = extractParamsHash(requestBody);

    return findOrInitializeFixture(method, requestPath, diagnostic, requestBody, paramsHash);
  }

  private static Fixture findOrInitializeFixture ( String method, String requestPath, String diagnostic, String requestBody, String paramsHash ) throws IOException {
    Fixture fixture;
    String filePath = filePath(method, requestPath, diagnostic, paramsHash);
    File file = new File(filePath);

    if ( file.isFile() && file.exists() ) {
      fixture = MAPPER.readValue(file, Fixture.class);
    } else {
      fixture = new Fixture();

      fixture.setDiagnostic(diagnostic);
      fixture.setMethod(method);
      fixture.setRequestBody(requestBody);
      fixture.setParamsHash(paramsHash);
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

  private static String extractParamsHash ( String requestBody ) throws NoSuchAlgorithmException {
    StringBuilder hex = new StringBuilder ();
    MessageDigest digest = MessageDigest.getInstance("SHA-256");
    
    if ( requestBody == null || requestBody.isEmpty() ) {
      return null;
    }
    
    for ( byte b : digest.digest(requestBody.getBytes(StandardCharsets.UTF_8))) {
      hex.append(String.format ("%02x", b));
    }
    
    return hex.substring(0, 7);
  }

  public static void write ( Fixture fixture ) throws IOException {
    String filePath = filePath(fixture.getMethod(), fixture.getRequestPath(), fixture.getDiagnostic(), fixture.getParamsHash());
    File file = new File(filePath);

    MAPPER.writeValue(file, fixture);
  }

  /**
   * Deletes the fixture file for the given method and request path.  Use with
   * caution, as this might make tests tricky to repeat.
   */
  public static boolean clobber ( String fileName ) {
    File file = new File(FIXTURES_PATH + File.separator + fileName + ".yml");

    if ( file.isFile() && file.exists() ) {
      return file.delete();
    }
    
    return false;
  }

  private static String filePath ( String method, String requestPath, String diagnostic, String paramsHash ) {
    String retVal = method + requestPath.replaceAll("[^a-zA-Z]+", "_");

    retVal = retVal.replaceAll("[_]+$", "");

    if ( diagnostic == null || diagnostic.isEmpty() ) {
      // This is probably a constructor test, so we can use an anonymous fixture.
      
      retVal = "anonymous_" + retVal;
    } else {
      retVal = diagnostic + "_" + retVal;
    }
    
    if ( paramsHash != null && !paramsHash.equals("0") ) {
      retVal += "_" + paramsHash;
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
  public String getParamsHash () { return paramsHash; }
  public void setParamsHash ( String paramsHash ) { this.paramsHash = paramsHash; }
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
