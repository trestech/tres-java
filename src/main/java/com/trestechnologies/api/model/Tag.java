package com.trestechnologies.api.model;

import com.trestechnologies.api.interfaces.APIContext;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class Tag extends BaseModel {
  public static ModelMapper<Tag> mapper ( ) { return new ModelMapper<Tag>() {
    @Override public Class<Tag> getModel () { return Tag.class; }
  }; }

  public static Tag find ( APIContext context, long recNo ) throws IOException {
    List<Tag> list = mapper().treeToList(context.get("Tag/" + recNo));
    
    return list.isEmpty() ? null : list.get(0);
  }

  public static List<Tag> search ( APIContext context, TagSearchParam params ) throws IOException {
    return mapper().treeToList(context.post("TagSearch", params));
  }

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

  private String description;
  
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
  
  public String getDescription () { return description; }
  
  public void setDescription ( String description ) { this.description = description; }
}
