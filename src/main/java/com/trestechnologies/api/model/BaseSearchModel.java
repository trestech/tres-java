package com.trestechnologies.api.model;

public class BaseSearchModel {
  private NumSearchParam recNo;
  
  private Long startingRow;
  
  private Long rowCount;
  
  private Long topRows;
  
  private Boolean distinct;

  private String[] includeCols;

//  private IncludeColsExtended includeColsExtended;

  private String baseUrl;

  private Long displayTagRecNo;

  //  private TagsSearchParams[] tags;
  
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
}
