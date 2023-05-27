package com.trestechnologies.api.model;

import com.trestechnologies.api.interfaces.AreaFlag;

import java.util.Arrays;

/**
 * Used when calling "TagSearch".
 */
public class TagSearchParam extends BaseSearchModel {
  private StringSearchParam name;
  
  private Boolean activeStatus;
  
  private Integer areaFlags;

  public TagSearchParam ( ) { }
  
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
