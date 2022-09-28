package com.trestechnologies.api.model;

public class StringSearchParam {
  private String[] value;

  private Short compareCondition;
  
  public enum Compare {
    IGNORE(0), EQUAL(1), NOT_EQUAL(2), BLANK(3), NOT_BLANK(4),
    STARTING_WITH(5), CONTAINS(6), IN(7), NOT_IN(8), NOT_STARTING_WITH(9),
    NOT_CONTAINING(10);
    
    public final short id;
    
    Compare ( int id ) { this.id = (short) id; }
  }

  public StringSearchParam ( String value ) {
    this.value = new String[] {value};
    this.compareCondition = Compare.EQUAL.id;
  }

  public StringSearchParam ( Compare compareCondition, String value ) {
    this.value = new String[] {value};
    this.compareCondition = compareCondition.id;
  }

  public StringSearchParam ( Compare compareCondition, String[] value ) {
    this(compareCondition);
    this.value = value;
  }

  public StringSearchParam ( Compare compareCondition ) {
    this.compareCondition = compareCondition.id;
  }

  public String[] getValue ( ) { return value; }

  public void setValue ( String[] value ) { this.value = value; }

  public Short getCompareCondition ( ) { return compareCondition; }

  public void setCompareCondition ( Compare compareCondition ) { this.compareCondition = compareCondition.id; }
  public void setCompareCondition ( Short compareCondition ) { this.compareCondition = compareCondition; }
}
