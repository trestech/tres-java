package com.trestechnologies.api.model;

import com.trestechnologies.api.interfaces.AreaFlag;

import java.util.Arrays;
import java.util.List;

public class TagSearchParam extends BaseSearchModel {
  StringSearchParam value;

  private StringSearchParam name;
  
  private Boolean activeStatus;
  
  private Integer areaFlags;

  public TagSearchParam ( ) { }

  public TagSearchParam ( Long recNo ) { setRecNo(recNo); }

  public TagSearchParam ( StringSearchParam value ) { this.value = value; }

  public TagSearchParam ( StringSearchParam.Compare compare, List<String> value ) { this.value = new StringSearchParam(compare, value); }

  public StringSearchParam getValue () { return value; }

  public void setValue ( StringSearchParam value ) { this.value = value; }
  public void setValue ( StringSearchParam.Compare compare, List<String> value ) { this.value = new StringSearchParam(compare, value); }
  public StringSearchParam getName () {
    return name;
  }

  public void setName ( StringSearchParam name ) { this.name = name; }
  public void setName ( String name ) { setName(new StringSearchParam(name)); }

  public Boolean getActiveStatus () {
    return activeStatus;
  }

  public void setActiveStatus ( Boolean activeStatus ) {
    this.activeStatus = activeStatus;
  }
  
  public Integer getAreaFlags () {
    return areaFlags;
  }

  public void setAreaFlags ( Integer areaFlags ) { this.areaFlags = areaFlags; }
  public void setAreaFlags ( AreaFlag... areaFlags ) { setAreaFlags(Arrays.stream(areaFlags).mapToInt(areaFlag -> areaFlag.flag).sum()); }
}
