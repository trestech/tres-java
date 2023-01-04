package com.trestechnologies.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trestechnologies.api.interfaces.APIContext;
import com.trestechnologies.api.interfaces.APIContextAdapter;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static com.trestechnologies.api.Methods.*;
import static java.util.logging.Level.FINE;
import static org.apache.http.HttpStatus.*;

/**
 * Provides access to the Tres API.
 * 
 * <blockquote><pre>
 *   TresContext context = new TresContext();
 *   context.login("username", "password", "PCC");
 *   System.out.println("Version: " + context.version());
 *   context.close();
 * </pre></blockquote>
 * 
 * Or with self closing pattern:
 * 
 * <blockquote><pre>
 *   new TresContext("username", "password", "PCC", (context) -> {
 *     System.out.println("Version: " + context.version());
 *   });
 * </pre></blockquote>
 */
public class TresContext extends APIContextAdapter {
  public static final String DEFAULT_URL = "https://api.trestechnologies.com";
  
  public static final String DEVELOP_URL = "https://api-dev.trestechnologies.com";
  
  private static final Logger LOG = Logger.getLogger(TresContext.class.toString());
  
  public static final String URL_SEP = "/";

  private final URL url;
  
  private final ObjectMapper mapper = new ObjectMapper();
  
  private final String diagnostic;

  private CredentialsProvider credentialsProvider;
  
  private String apiVersion;
  
  private String token;

  private CloseableHttpClient client;

  public TresContext ( ) throws IOException {
    this(DEFAULT_URL, null, null);
  }

  public TresContext ( String username, String password, String domain, Group<APIContext> group ) throws IOException {
    this(DEFAULT_URL, username, password, domain);
    batch(group);
  }

  public TresContext ( Group<APIContext> group ) throws IOException {
    this(DEFAULT_URL, null, null);
    batch(group);
  }

  public TresContext ( String url ) throws IOException {
    this(url, null, null);
  }

  public TresContext ( String url, Group<APIContext> group ) throws IOException {
    this(url, null, null);
    batch(group);
  }

  public TresContext ( String url, String token, String diagnostic ) throws IOException {
    this(new URL(url), token, diagnostic);
  }

  public TresContext ( String url, String token ) throws IOException {
    this(new URL(url), token, null);
  }

  public TresContext ( String url, String username, String password, String domain, String diagnostic ) throws IOException {
    this(new URL(url), username, password, domain, diagnostic);
  }

  public TresContext ( String url, String username, String password, String domain, Group<APIContext> group ) throws IOException {
    this(new URL(url), username, password, domain, null);
    batch(group);
  }

  public TresContext ( String url, String username, String password, String domain ) throws IOException {
    this(new URL(url), username, password, domain, null);
  }

  public TresContext ( URL url, String username, String password, String domain, String diagnostic ) throws IOException {
    this(url, null, diagnostic);

    login(username, password, domain);
  }

  public TresContext ( URL url, String username, String password, String domain ) throws IOException {
    this(url, null, null);

    login(username, password, domain);
  }

  public TresContext ( URL url, String token, String diagnostic ) throws IOException {
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    
    this.url = url;
    this.token = token;
    this.diagnostic = diagnostic;
    
    version();
  }
  
  @Override public URL getUrl ( ) { return url; }

  @Override public String getToken ( ) { return token; }

  @Override public String getApiVersion ( ) { return apiVersion; }

  @Override
  public JsonNode login ( String username, String password, String domain ) throws IOException {
    JsonNode result = getWithBasicAuth(LOGIN + URL_SEP + domain, username, password);
    
    this.token = result.get("identityToken").textValue();
    
    return result;
  }

  @Override
  public JsonNode logout ( ) throws IOException {
    JsonNode result = get(LOGOUT);
    
    this.token = null;
    
    return result;
  }

  @Override
  public JsonNode refreshIdentityToken ( ) throws IOException {
    JsonNode result = get(REFRESH_IDENTITY_TOKEN);
    
    this.token = result.get("refreshIdentityToken").textValue();
    
    return result;
  }

  @Override
  public JsonNode version ( ) throws IOException {
    JsonNode result = get(VERSION);
    
    this.apiVersion = result.get("version").textValue();
      
    return result;
  }
  
  @Override
  public JsonNode getWithBasicAuth ( String method, String username, String password, RequestHeaders headers ) throws IOException {
    HttpUriRequest request = new HttpGet(buildRequestUrl(method));
    UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username, password);
    AuthScope authScope = new AuthScope(url.getHost(), url.getPort());
    
    credentialsProvider = new BasicCredentialsProvider();
    credentialsProvider.setCredentials(authScope, credentials);
    
    return execute(request, headers);
  }
  
  @Override
  public JsonNode getWithBasicAuth ( String method, String username, String password ) throws IOException {
    return getWithBasicAuth(method, username, password, null);
  }
  
  @Override
  public JsonNode get ( String method, RequestHeaders headers ) throws IOException {
    HttpUriRequest request = new HttpGet(buildRequestUrl(method));

    return execute(request, headers);
  }

  @Override
  public JsonNode get ( String method ) throws IOException {
    return get(method, null);
  }
  
  @Override
  public JsonNode post ( String method, Object object, RequestHeaders headers ) throws IOException {
    HttpPost request = new HttpPost(buildRequestUrl(method));
    
    if ( object != null ) {
      String json;
      
      if ( object instanceof JsonNode || object instanceof String ) {
        json = object.toString();
      } else {
        json = mapper.writeValueAsString(object);
      }
      
      if ( LOG.isLoggable(FINE) ) {
        json = toJson(mapper.readTree(json));
        
        LOG.fine("post -> " + json);
      }
      
      request.setEntity(new StringEntity(json));
    }
    
    return execute(request, null);
  }
  
  @Override
  public JsonNode post ( String method, Object object ) throws IOException {
    return post(method, object, null);
  }
  
  @Override
  public void batch ( Group<APIContext> group ) throws IOException {
    group.by(this);
    close();
  }
  
  @Override
  public void close ( ) throws IOException {
    if ( client != null ) {
      client.close();
      client = null;
    }
  }

  private String buildRequestUrl ( String method ) {
    String methodUrl = url.toString();
    StringBuilder retVal = new StringBuilder(methodUrl);
    
    if ( !methodUrl.endsWith(URL_SEP) ) {
      retVal.append(URL_SEP);
    }
    
    retVal.append(method);
    
    return new String(retVal);
  }

  private JsonNode execute ( HttpUriRequest request, RequestHeaders headers ) throws IOException {
    AuthCache authCache = new BasicAuthCache();
    BasicScheme basicAuth = new BasicScheme();
    HttpHost target = new HttpHost(url.getHost(), url.getPort(), url.getProtocol());
    HttpClientContext localContext = HttpClientContext.create();
    
    authCache.put(target, basicAuth);
    localContext.setCredentialsProvider(credentialsProvider);
    localContext.setAuthCache(authCache);

    try {
      if ( client == null ) {
        client = HttpClients.createDefault();
      }

      if ( headers == null ) {
        ((RequestHeaders) (ignore) -> {
          if ( getToken() != null ) {
            request.setHeader("Authorization", "Bearer " + getToken());
          }
        }).andThen(request, diagnostic);
      } else {
        headers.andThen(request, diagnostic);
      }

      HttpResponse response = client.execute(target, request, localContext);
      InputStream stream = response.getEntity().getContent();
      JsonNode node = mapper.readTree(stream);
      
      if ( node.isObject() && !node.has("resultCode") ) {
        String message = "No result code for request: " + request;
        
        if ( LOG.isLoggable(FINE) ) {
          message += "\n" + toJson(node);

          LOG.warning(message);
        }
      }
      
      switch ( response.getStatusLine().getStatusCode() ) {
        case SC_OK: return node;
        case SC_BAD_REQUEST: case SC_UNAUTHORIZED: 
          if ( node.isObject() && node.has("resultCode") ) {
            int code = node.get("resultCode").intValue();

            if ( code != 0 ) {
              throw new TresException(code, node.toString());
            }
          }
          
          throw new TresException("Unknown error: " + node);
        default:
          String body = new BufferedReader(new InputStreamReader(stream)).lines().collect(Collectors.joining("\n"));
          
          throw new TresException(response.getStatusLine() + ": " + request + " " + body);
      }
    } catch ( JsonMappingException e ) {
      throw new TresException("Unable to map response", e);
    } catch ( IOException | TresException e ) {
      throw e;
    } catch ( Exception e ) {
      throw new TresException("Unable to execute request", e);
    }
  }
  
  private String toJson ( JsonNode node ) {
    Class<? extends JsonNode> c = node.getClass();

    try {
      Method m = c.getMethod("toPrettyString");

      return m.invoke(node).toString();
    } catch ( NoSuchMethodException | IllegalAccessException | InvocationTargetException e ) {
      return node.toString();
    }
  }
}
