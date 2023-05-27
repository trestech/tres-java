package com.trestechnologies.api.model;

import java.util.List;

/**
 * Used when doing model searches that inherit from BaseSearchModel.
 */
public class TagsSearchParams {
  private Long recNo;
  StringSearchParam value;
  
  public TagsSearchParams ( ) { }
  
  public TagsSearchParams ( Long recNo ) { this.recNo = recNo; }

  public TagsSearchParams ( StringSearchParam value ) { this.value = value; }
  
  public TagsSearchParams ( StringSearchParam.Compare compare, List<String> value ) { this.value = new StringSearchParam(compare, value); }

  public Long getRecNo () { return recNo; }

  public void setRecNo ( Long recNo ) { this.recNo = recNo; }

  public StringSearchParam getValue () { return value; }
  
  public void setValue ( StringSearchParam value ) { this.value = value; }
  public void setValue ( StringSearchParam.Compare compare, List<String> value ) { this.value = new StringSearchParam(compare, value); }
}
