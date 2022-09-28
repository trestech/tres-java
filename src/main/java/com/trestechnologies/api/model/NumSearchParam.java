package com.trestechnologies.api.model;

public class NumSearchParam {
  private Long[] value;

  private Short compareCondition;

  public enum Compare {
    IGNORE(0), EQUAL(1), NOT_EQUAL(2), BLANK(3), NOT_BLANK(4);
    
    public final short id;
    
    Compare ( int id ) { this.id = (short) id; }
  }

  public NumSearchParam ( long value ) {
    this.value = new Long[] {value};
    this.compareCondition = Compare.EQUAL.id;
  }

  public NumSearchParam ( Compare compareCondition, long value ) {
    this(compareCondition, new Long[] {value});
  }
  
  public NumSearchParam ( Compare compareCondition, Long[] value ) {
    this(compareCondition);
    this.value = value;
  }

  public NumSearchParam ( Compare compareCondition ) {
    this.compareCondition = compareCondition.id;
  }

  public NumSearchParam ( Long[] value ) {
    this(Compare.EQUAL, value);
  }

  public Long[] getValue ( ) { return value; }

  public void setValue ( Long[] value ) { this.value = value; }

  public Short getCompareCondition ( ) { return compareCondition; }

  public void setCompareCondition ( Compare compareCondition ) { this.compareCondition = compareCondition.id; }
  public void setCompareCondition ( Short compareCondition ) { this.compareCondition = compareCondition; }
}
