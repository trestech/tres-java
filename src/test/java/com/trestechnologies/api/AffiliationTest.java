package com.trestechnologies.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.trestechnologies.api.model.Affiliation;

import java.util.List;

public class AffiliationTest extends BaseTestCase {
  public void testGetTres ( ) { ((WithMockWebServer) ( context ) -> {
    List<Affiliation> list;
    Affiliation affiliation;
    
    list = Affiliation.MAPPER.treeToList(context.get("Affiliation/1"));
    
    assertEquals("expect result to have one element", 1, list.size());
    
    affiliation = list.get(0);
    assertEquals(1, affiliation.getRecNo());
    assertEquals("Tres", affiliation.getName());
  }).group("affiliation_get_tres"); }
  
  public void testGetUnknown ( ) { ((WithMockWebServer) ( context ) -> {
    try {
      Affiliation.MAPPER.treeToList(context.get("Affiliation/99"));
    } catch ( TresException ignore ) {
      // success
    }
  }).group("affiliation_get_unknown"); }
}
