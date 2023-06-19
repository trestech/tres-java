package com.trestechnologies.api;

import com.trestechnologies.api.model.Affiliation;

public class AffiliationTest extends BaseTestCase {
  public void testGetTres ( ) { ((WithMockWebServer) context -> {
    Affiliation affiliation = Affiliation.find(context, 1);
    
    assertEquals(1, affiliation.getRecNo());
    assertEquals("Tres", affiliation.getName());
  }).group("affiliation_get_tres"); }
  
  public void testGetSignatureTravelNetwork ( ) { ((WithMockWebServer) context -> {
    Affiliation affiliation = Affiliation.find(context, 2);
    
    assertEquals(2, affiliation.getRecNo());
    assertEquals("Signature Travel Network", affiliation.getName());
  }).group("affiliation_get_signature_travel_network"); }
  
  public void testGetUnknown ( ) { ((WithMockWebServer) context -> {
    try {
      Affiliation.find(context, 99);
    } catch ( TresException ignore ) {
      // success
    }
  }).group("affiliation_get_unknown"); }
}
