package com.trestechnologies.api;

import com.trestechnologies.api.annotation.ExitOnError;

import java.util.concurrent.atomic.AtomicBoolean;

@ExitOnError
public class CBMSListPullTest extends BaseTestCase {
  public void testPop ( ) { ((WithMockWebServer) context -> {
    CBMSListPull listPull = new CBMSListPull(context, "EML01");
    AtomicBoolean ran = new AtomicBoolean(false);

    listPull.forEach((profile, priority) -> {
      assert profile != null : "profile is null";
      
      assertEquals("expect priority to be 0", priority, 0);
      
      assert profile.getStateProvince() != null : "state province is null";
      assert !profile.getStateProvince().isEmpty() : "state province is empty for: " + profile.getPrimaryEmail();
      
      ran.set(true);
    });
    
    assert ran.get() : "did not run";
  }).group("cbms_list_pull_pop"); }
}
