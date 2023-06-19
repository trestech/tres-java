package com.trestechnologies.api;

import com.trestechnologies.api.model.AppSetting;
import com.trestechnologies.api.model.AppSettingSearchParam;

import java.util.List;

public class AppSettingTest extends BaseTestCase {
  public void testQuery ( ) { ((WithMockWebServer) context -> {
    List<AppSetting> settings;
    AppSettingSearchParam params = new AppSettingSearchParam();
    
    params.setStartingRow(0);
    
    settings = AppSetting.search(context, params);
    
    assert !settings.isEmpty() : "did not expect empty result";
  }).group("app_setting_query"); }
  
  public void testQueryArea ( ) { ((WithMockWebServer) context -> {
    List<AppSetting> settings;
    AppSettingSearchParam params = new AppSettingSearchParam();

    params.setStartingRow(0);
    params.setArea("clientProfileSearch");

    settings = AppSetting.search(context, params);

    assert !settings.isEmpty() : "did not expect empty result";
  }).group("app_setting_query_area"); }
}
