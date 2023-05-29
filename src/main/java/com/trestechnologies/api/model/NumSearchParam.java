package com.trestechnologies.api.model;

import java.util.ArrayList;
import java.util.List;

public class NumSearchParam {
  private List<Long> value;

  private Short compareCondition;

  public enum Compare {
    IGNORE(0), EQUAL(1), NOT_EQUAL(2), BLANK(3), NOT_BLANK(4);
    
    public final short id;
    
    Compare ( int id ) { this.id = (short) id; }
  }

  public NumSearchParam ( long value ) {
    this.value = new ArrayList<Long>() {{ add(value); }};
    this.compareCondition = Compare.EQUAL.id;
  }

  public NumSearchParam ( Compare compareCondition, long value ) { this(compareCondition, new ArrayList<Long>() {{ add(value); }}); }
  
  public NumSearchParam ( Compare compareCondition, List<Long> value ) {
    this(compareCondition);
    this.value = value;
  }

  public NumSearchParam ( Compare compareCondition ) {
    this.compareCondition = compareCondition.id;
  }

  public NumSearchParam ( List<Long> value ) {
    this(Compare.EQUAL, value);
  }

  public List<Long> getValue ( ) { return value; }

  public void setValue ( List<Long> value ) { this.value = value; }

  public Short getCompareCondition ( ) { return compareCondition; }

  public void setCompareCondition ( Compare compareCondition ) { this.compareCondition = compareCondition.id; }
  public void setCompareCondition ( Short compareCondition ) { this.compareCondition = compareCondition; }
}
