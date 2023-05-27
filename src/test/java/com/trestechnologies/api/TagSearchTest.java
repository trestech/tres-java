package com.trestechnologies.api;

import com.trestechnologies.api.model.Tag;
import com.trestechnologies.api.model.TagSearchParam;
import com.trestechnologies.api.interfaces.AreaFlag;

import java.util.Arrays;
import java.util.List;

public class TagSearchTest extends BaseTestCase {
  public void testQuery ( ) { ((WithMockWebServer) ( context ) -> {
    List<Tag> tags;
    TagSearchParam params = new TagSearchParam();

    params.setStartingRow(0);

    tags = Tag.MAPPER.treeToList(context.post("TagSearch", params));

    assert !tags.isEmpty() : "did not expect empty result";
  }).group("tag_search_query"); }

  public void testQueryNone ( ) { ((WithMockWebServer) ( context ) -> {
    List<Tag> tags;
    TagSearchParam params = new TagSearchParam();

    params.setName("ZzZzZ");

    tags = Tag.MAPPER.treeToList(context.post("TagSearch", params));
    
    assert tags.isEmpty() : "expect empty result";
  }).group("tag_search_query_none"); }

  public void testQueryNoMarketing ( ) { ((WithMockWebServer) ( context ) -> {
    TagSearchParam params = new TagSearchParam();
    int expectedAreaFlags = AreaFlag.CLIENT.flag | AreaFlag.TRAVELER.flag, actualAreaFlags;
    List<Tag> tags;
    Tag tag;
    
    params.setStartingRow(0);
    params.setRowCount(1);
    params.setTopRows(1);
    params.setAreaFlags(AreaFlag.CLIENT, AreaFlag.TRAVELER);
    params.setName("No Marketing");

    tags = Tag.MAPPER.treeToList(context.post("TagSearch", params));

    assert !tags.isEmpty() : "expect to find the tag";
    
    tag = tags.get(0);

    String[] expected = "NOCBM\nNOTBM\nNOMKT\nNOEMM\nBDATA".split("\n");
    String[] actual = tag.getValueList().split("\n");

    Arrays.sort(expected);
    Arrays.sort(actual);

    assert Arrays.equals(expected, actual) : "expect to find the same marketing codes but got: " + Arrays.toString(actual);
    
    actualAreaFlags = tag.getAreaFlags();
    
    assertEquals("expect to find the same area flags", expectedAreaFlags, actualAreaFlags);
    assert !AreaFlag.hasFlag(actualAreaFlags, AreaFlag.ALL) : "did not expect to find the all area flag";
    assert AreaFlag.hasFlag(actualAreaFlags, AreaFlag.CLIENT) : "expect to find the client area flag";
    assert AreaFlag.hasFlag(actualAreaFlags, AreaFlag.TRAVELER) : "expect to find the traveler area flag";
    assert !AreaFlag.hasFlag(actualAreaFlags, AreaFlag.ADVISOR_ADJUSTMENT) : "did not expect to find the advisor adjustment area flag";
    
    AreaFlag.of(tag.getAreaFlags()).forEach(areaFlag -> {
      assert areaFlag == AreaFlag.CLIENT || areaFlag == AreaFlag.TRAVELER : "expect to find only client and traveler area flags but got: " + areaFlag;
    });
  }).group("tag_search_query_no_marketing"); }
}
