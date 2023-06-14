package com.trestechnologies.api;

import java.util.concurrent.atomic.AtomicBoolean;

public class CBMSListPullTest extends BaseTestCase {
  public void testPop ( ) { ((WithMockWebServer) ( context ) -> {
    CBMSListPull listPull = new CBMSListPull(context);
    AtomicBoolean ran = new AtomicBoolean(false);

    listPull.pop("EML01", (profile, priority) -> {
      assert profile != null : "profile is null";
      assert priority != null : "priority is null";
      
      assertEquals("expect priority to be 0", priority, "0");
      
      ran.set(true);
    });
    
    assert ran.get() : "did not run";
  }).group("cbms_list_pull_pop"); }
}
