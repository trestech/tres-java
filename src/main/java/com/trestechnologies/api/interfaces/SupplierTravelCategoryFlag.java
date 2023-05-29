package com.trestechnologies.api.interfaces;

import java.util.EnumSet;
import java.util.Set;

/**
 * You can combine multiple area categories to select more than one.  This will
 * produce an integer outside the range of the enum values, which is based
 * on the bit position of the ordinal.  For example, if you want to
 * select both CRUISE and INSURANCE, you would use the following:
 * 
 * <pre><code>
 *   int selectedCategories = SupplierTravelCategoryFlag.CRUISE.flag | SupplierTravelCategoryFlag.INSURANCE.flag
 * </code></pre>
 */
public enum SupplierTravelCategoryFlag implements Flaggable<SupplierTravelCategoryFlag> {
  ALL, AIR, HOTEL, CAR, CRUISE, TOUR, RAIL, TRANSFER, INSURANCE, SERVICE_FEE,
    EXCURSION;
  
  public final int flag;

  SupplierTravelCategoryFlag ( ) { this.flag = 1 << ordinal() - 1; }
  
  @Override
  public int getFlag () { return this.flag; }

  @Override
  public Set<SupplierTravelCategoryFlag> of ( int flags ) {
    EnumSet<SupplierTravelCategoryFlag> result = EnumSet.noneOf(SupplierTravelCategoryFlag.class);

    for ( SupplierTravelCategoryFlag flag : SupplierTravelCategoryFlag.values() ) {
      if ( Flaggable.hasFlag(flags, flag) ) {
        result.add(flag);
      }
    }

    if ( flags != 0 ) {
      result.remove(SupplierTravelCategoryFlag.ALL);
    }

    return result;
  }
}
