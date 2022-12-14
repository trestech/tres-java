package com.trestechnologies.api;

import com.trestechnologies.api.interfaces.APIContext;
import junit.framework.TestCase;
import kotlin.Pair;
import okhttp3.HttpUrl;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.stream.Collectors;

public abstract class BaseTestCase extends TestCase {
  /**
   * Fixes that are pending should skip at this point until they are fixed.
   *
   * Leave this true if we're not currently working on these issues.
   */
  protected static final boolean SKIP_PENDING_FIX = true;

  protected static final String LIVE_URL = "https://api-dev.trestechnologies.com";
  
  protected static final String USERNAME = "username";

  protected static final String PASSWORD = "password";

  protected static final String DOMAIN = "domain";

  protected static final String TOKEN = "ew0KICAiYWxnIjogIkhTMjU2IiwNCiAgInR5cCI6ICJKV1QiDQp9.ew0KICAiZXhwaXJlRGF0ZSI6ICIyMDIyLTA5LTA4VDIwOjA5OjQwLjMyNjIxMjUrMDA6MDAiLA0KICAiZXhwaXJlSW50ZXJ2YWwiOiAzMCwNCiAgImFnZW5jeVJlY05vIjogMjMsDQogICJhcHBVc2VyUmVjTm8iOiAxODI1LA0KICAiYWRtaW5Vc2VyUmVjTm8iOiBudWxsLA0KICAidXNlck5hbWUiOiAiU0hBUk9OIiwNCiAgImFsaWFzIjogIkFWREIiLA0KICAidG9rZW5SZWNObyI6IDE2MTEyMywNCiAgImFwcE5hbWUiOiAiV2ViIEFQSSIsDQogICJjbGllbnRJUEFkZHJlc3MiOiAiMTAuMS4yLjQiLA0KICAiYWZmaWxpYXRpb25SZWNObyI6IG51bGwNCn0.5-zkxXuEyGv8BHmubHNuksm5JntrenyW4tQpq0KE1VA";
  
  private static final String[] REQUEST_HEADER_WHITELIST = new String[] {
    "Content-Type",
    "Authorization"
  };

  private static final String[] RESPONSE_HEADER_WHITELIST = new String[] {
    "Content-Type"
  };
  
  @FunctionalInterface
  interface WithMockWebServer extends APIContext.Group<APIContext> {
    void by ( APIContext context ) throws IOException;
    
    default void group ( String by ) {
      try ( MockWebServer server = new MockWebServer() ) {
        HttpUrl mockUrl;
        TresContext context;

        server.start();
        server.setDispatcher(DISPATCHER);
        mockUrl = server.url("");
        //mockUrl = HttpUrl.get(LIVE_URL);
        //context = new TresContext(mockUrl.url(), USERNAME, PASSWORD, DOMAIN, by);
        context = new TresContext(mockUrl.url(), TOKEN, by);
        
        context.batch(this);
        
        try {
          assertEquals(0, context.logout().get("resultCode").intValue());
        } catch ( TresException e ) {
          // The test already logged out, so this had better be the reason.  If not, we have a different error mode to deal
          // with.
          assertEquals("{\"resultCode\":302,\"resultDescription\":\"Identity token missing\",\"method\":\"identityToken\"}", e.getMessage());
        }
      } catch ( AssertionError e ) {
        throw e;
      } catch ( Exception e ) {
        e.printStackTrace();
        
        fail(e.getMessage());
      }
    }
  }
  
  protected static final Dispatcher DISPATCHER = new Dispatcher() {
    @NotNull @Override
    public MockResponse dispatch ( @NotNull RecordedRequest request ) {
      Fixture fixture;
      
      try {
        fixture = Fixture.read(request);
        
        if ( fixture.getStatus() != null ) {
          MockResponse mockResponse = new MockResponse();
          
          mockResponse.setBody(fixture.getBody());
          mockResponse.setStatus(fixture.getStatus());
          
          if ( fixture.getResponseHeaders() != null ) {
            fixture.getResponseHeaders().forEach(mockResponse::setHeader);
          }
          
          return mockResponse;
        }
      } catch(Exception e ) {
        e.printStackTrace();
        
        return new MockResponse().
          setResponseCode(HttpURLConnection.HTTP_INTERNAL_ERROR).
          setBody(e.toString());
      }
      
      try ( CloseableHttpClient liveClient = HttpClients.createDefault() ) {
        URL url = new URL(LIVE_URL + request.getPath());
        HttpResponse liveResponse;
        HttpHost target = new HttpHost(url.getHost(), url.getPort(), url.getProtocol());
        HttpClientContext localContext = HttpClientContext.create();
        HttpUriRequest liveRequest;
        
        fixture.setUrl(url.toString());
        
        if ( fixture.getMethod().equals("GET") ) {
          liveRequest = new HttpGet(fixture.getUrl());
        } else if ( fixture.getMethod().equals("POST") ) {
          HttpPost livePostRequest;
          
          fixture.setRequestBody(request.getBody().readUtf8());
          liveRequest = livePostRequest = new HttpPost(fixture.getUrl());
          
          livePostRequest.setEntity(new StringEntity(fixture.getRequestBody()));
        } else {
          throw new RuntimeException("Unsupported method: " + fixture.getMethod());
        }
        
        for ( Pair<? extends String, ? extends String> header : request.getHeaders() ) {
          String key = header.getFirst();
          String val = header.getSecond();
          
          if ( !Arrays.asList(REQUEST_HEADER_WHITELIST).contains(key) ) { continue; }

          liveRequest.setHeader(key, val);
          fixture.getRequestHeaders().put(key, val);
        }
        
        liveResponse = liveClient.execute(target, liveRequest, localContext);

        for ( Header header : liveResponse.getAllHeaders() ) {
          String key = header.getName();
          String val = header.getValue();

          if ( !Arrays.asList(RESPONSE_HEADER_WHITELIST).contains(key) ) { continue; }

          fixture.getResponseHeaders().put(key, val);
        }
        
        fixture.setBody(
          new BufferedReader(new InputStreamReader(liveResponse.getEntity().getContent())).lines().
            collect(Collectors.joining("\n"))
        );

        fixture.setStatus(liveResponse.getStatusLine().toString());
      } catch ( Exception e ) {
        e.printStackTrace();
        
        return new MockResponse().
          setResponseCode(HttpURLConnection.HTTP_NOT_FOUND).
          setBody("Could not find upstream: " + request);
      }
      
      try {
        Fixture.write(fixture);
      } catch ( IOException e ) {
        e.printStackTrace();

        return new MockResponse().
          setResponseCode(HttpURLConnection.HTTP_INTERNAL_ERROR).
          setBody(e.toString());
      }
      
      MockResponse mockResponse = new MockResponse().
        setBody(fixture.getBody()).
        setStatus(fixture.getStatus());
      
      fixture.getResponseHeaders().forEach(mockResponse::setHeader);
      
      return mockResponse;
    }
  };
}
