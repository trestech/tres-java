package com.trestechnologies.api.model;

import java.util.Date;

public class Tag {
  public static final ModelMapper<Tag> MAPPER = new ModelMapper<Tag>() {
    @Override public Class<Tag> getModel () { return Tag.class; }
  };
  
  private long recNo;

  private String name;

  private Boolean activeStatus;

  private Boolean valueFreeFlow;

  private Boolean valueRequired;

  private String valueList;

  private Integer areaFlags;

  private Short requirement;

  private String defaultValue;

  private Date createDateTime;
  
  private Date lastModifiedDateTime;
  
  private Boolean referenced;

  public Tag ( ) { }

  public long getRecNo () { return recNo; }

  public void setRecNo ( long recNo ) { this.recNo = recNo; }

  public String getName () { return name; }

  public void setName ( String name ) { this.name = name; }

  public Boolean getActiveStatus () { return activeStatus; }

  public void setActiveStatus ( Boolean activeStatus ) { this.activeStatus = activeStatus; }

  public Boolean getValueFreeFlow () { return valueFreeFlow; }

  public void setValueFreeFlow ( Boolean valueFreeFlow ) { this.valueFreeFlow = valueFreeFlow; }

  public Boolean getValueRequired () { return valueRequired; }

  public void setValueRequired ( Boolean valueRequired ) { this.valueRequired = valueRequired; }

  public String getValueList () { return valueList; }

  public void setValueList ( String valueList ) { this.valueList = valueList; }

  public Integer getAreaFlags () { return areaFlags; }

  public void setAreaFlags ( Integer areaFlags ) { this.areaFlags = areaFlags; }

  public Short getRequirement () { return requirement; }

  public void setRequirement ( Short requirement ) { this.requirement = requirement; }

  public String getDefaultValue () { return defaultValue; }

  public void setDefaultValue ( String defaultValue ) { this.defaultValue = defaultValue; }

  public Date getCreateDateTime () { return createDateTime; }

  public void setCreateDateTime ( Date createDateTime ) { this.createDateTime = createDateTime; }
  
  public Date getLastModifiedDateTime () { return lastModifiedDateTime; }
  
  public void setLastModifiedDateTime ( Date lastModifiedDateTime ) { this.lastModifiedDateTime = lastModifiedDateTime; }
  
  public Boolean getReferenced () { return referenced; }
  
  public void setReferenced ( Boolean referenced ) { this.referenced = referenced; }
}
