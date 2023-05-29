package com.trestechnologies.api.model;

import com.trestechnologies.api.interfaces.APIContext;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class AppSetting extends BaseModel {
  public static ModelMapper<AppSetting> mapper ( ) { return new ModelMapper<AppSetting>() {
    @Override public Class<AppSetting> getModel () { return AppSetting.class; }
  }; }

  public static List<AppSetting> search ( APIContext context, AppSettingSearchParam params ) throws IOException {
    return mapper().treeToList(context.post("AppSettingSearch", params));
  }

  public enum UserType { UNKNOWN, USER, MANAGER, ADMIN }
  
  private long recNo;
  private long appUser_recNo;
  private String area;
  private String name;
  private String description;
  private String data;
  private UserType userType;
  private Date createDateTime;
  private Date lastModifiedDateTime;
  
  public long getRecNo () {
    return recNo;
  }

  public void setRecNo ( long recNo ) {
    this.recNo = recNo;
  }

  public long getAppUser_recNo () {
    return appUser_recNo;
  }

  public void setAppUser_recNo ( long appUser_recNo ) {
    this.appUser_recNo = appUser_recNo;
  }

  public String getArea () {
    return area;
  }

  public void setArea ( String area ) {
    this.area = area;
  }

  public String getName () {
    return name;
  }

  public void setName ( String name ) {
    this.name = name;
  }

  public String getDescription () {
    return description;
  }

  public void setDescription ( String description ) {
    this.description = description;
  }

  public String getData () {
    return data;
  }

  public void setData ( String data ) {
    this.data = data;
  }

  public UserType getUserType () {
    return userType;
  }

  public void setUserType ( UserType userType ) {
    this.userType = userType;
  }

  public Date getCreateDateTime () {
    return createDateTime;
  }

  public void setCreateDateTime ( Date createDateTime ) {
    this.createDateTime = createDateTime;
  }

  public Date getLastModifiedDateTime () {
    return lastModifiedDateTime;
  }

  public void setLastModifiedDateTime ( Date lastModifiedDateTime ) {
    this.lastModifiedDateTime = lastModifiedDateTime;
  }
}
