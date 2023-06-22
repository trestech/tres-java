package com.trestechnologies.api.model;

/**
 * Used when calling "AppSettingSearch".
 */
public class AppSettingSearchParam extends BaseSearchModel {
  private String area;

  public String getArea () {
    return area;
  }

  public void setArea ( String area ) {
    this.area = area;
  }
}
