package com.trestechnologies.api.model;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseSearchModel extends BaseModel {
  /**
   * Used for filtering by tag when searching other objects.
   */
  public static class TagSearchParam {
    public TagSearchParam ( ) { }

    public TagSearchParam ( Long recNo, StringSearchParam.Compare compare, List<String> value ) {
      this.recNo = recNo;
      this.value = new StringSearchParam(compare, value);
    }

    public TagSearchParam ( Long recNo, StringSearchParam.Compare compare, String value ) {
      this.recNo = recNo;
      this.value = new StringSearchParam(compare, value);
    }
    
    public TagSearchParam ( Long recNo, String value ) {
      this(recNo, StringSearchParam.Compare.EQUAL, new ArrayList<String>() {{ add(value); }});
    }
    private Long recNo;

    private StringSearchParam value;

    public Long getRecNo () {
      return recNo;
    }

    public void setRecNo ( Long recNo ) {
      this.recNo = recNo;
    }

    public StringSearchParam getValue () {
      return value;
    }

    public void setValue ( StringSearchParam value ) {
      this.value = value;
    }
  }

  private NumSearchParam recNo;
  
  private Long startingRow;
  
  private Long rowCount;
  
  private Long topRows;
  
  private Boolean distinct;

  private String[] includeCols;

//  private IncludeColsExtended includeColsExtended;

  private String baseUrl;

  private Long displayTagRecNo;

  private List<TagSearchParam> tags;
  
  public NumSearchParam getRecNo ( ) { return recNo; }

  public void setRecNo ( NumSearchParam recNo ) { this.recNo = recNo; }
  public void setRecNo ( long recNo ) { this.recNo = new NumSearchParam(recNo); }

  public Long getStartingRow ( ) { return startingRow; }

  public void setStartingRow ( Long startingRow ) { this.startingRow = startingRow; }
  public void setStartingRow ( long startingRow ) { this.startingRow = startingRow; }

  public Long getRowCount ( ) { return rowCount; }

  public void setRowCount ( Long rowCount ) { this.rowCount = rowCount; }
  public void setRowCount ( long rowCount ) { this.rowCount = rowCount; }

  public Long getTopRows ( ) { return topRows; }

  public void setTopRows ( Long topRows ) { this.topRows = topRows; }
  public void setTopRows ( long topRows ) { this.topRows = topRows; }

  public Boolean getDistinct ( ) { return distinct; }

  public void setDistinct ( Boolean distinct ) { this.distinct = distinct; }

  public String[] getIncludeCols ( ) { return includeCols; }

  public void setIncludeCols ( String[] includeCols ) { this.includeCols = includeCols; }

  public String getBaseUrl ( ) { return baseUrl; }

  public void setBaseUrl ( String baseUrl ) { this.baseUrl = baseUrl; }

  public Long getDisplayTagRecNo ( ) { return displayTagRecNo; }

  public void setDisplayTagRecNo ( Long displayTagRecNo ) { this.displayTagRecNo = displayTagRecNo; }
  public void setDisplayTagRecNo ( long displayTagRecNo ) { this.displayTagRecNo = displayTagRecNo; }

  public List<TagSearchParam> getTags () { return tags; }

  public void setTags ( List<TagSearchParam> tags ) { this.tags = tags; }
  public void setTags ( TagSearchParam tags ) {
    this.tags = new ArrayList<>();
    
    this.tags.add(tags);
  }
  public void setTags ( Long recNo, StringSearchParam.Compare compare, List<String> value ) {
    this.setTags(new TagSearchParam(recNo, compare, value));
  }
}
