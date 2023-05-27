package com.trestechnologies.api.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringSearchParam {
  private List<String> value;

  private Short compareCondition;
  
  public enum Compare {
    IGNORE(0), EQUAL(1), NOT_EQUAL(2), BLANK(3), NOT_BLANK(4),
    STARTING_WITH(5), CONTAINS(6), IN(7), NOT_IN(8), NOT_STARTING_WITH(9),
    NOT_CONTAINING(10);
    
    public final short id;
    
    Compare ( int id ) { this.id = (short) id; }
  }

  public StringSearchParam ( String value ) {
    this.value = Arrays.stream(new String[] {value}).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    this.compareCondition = Compare.EQUAL.id;
  }

  public StringSearchParam ( Compare compareCondition, String value ) {
    this.value = Arrays.stream(new String[] {value}).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    this.compareCondition = compareCondition.id;
  }

  public StringSearchParam ( Compare compareCondition, List<String> value ) {
    this(compareCondition);
    this.value = value;
  }

  public StringSearchParam ( Compare compareCondition ) {
    this.compareCondition = compareCondition.id;
  }

  public List<String> getValue ( ) { return value; }

  public void setValue ( List<String> value ) { this.value = value; }

  public Short getCompareCondition ( ) { return compareCondition; }

  public void setCompareCondition ( Compare compareCondition ) { this.compareCondition = compareCondition.id; }
  public void setCompareCondition ( Short compareCondition ) { this.compareCondition = compareCondition; }
}
