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
public enum SupplierTravelCategoryFlag {
  ALL, AIR, HOTEL, CAR, CRUISE, TOUR, RAIL, TRANSFER, INSURANCE, SERVICE_FEE,
    EXCURSION;
  
  public final Integer flag;

  SupplierTravelCategoryFlag ( ) { this.flag = 1 << ordinal() - 1; }
  
  public static boolean hasFlag ( Integer flags, SupplierTravelCategoryFlag flag ) {
    return flags != null && (flags & flag.flag) == flag.flag;
  }
    
  public static Set<SupplierTravelCategoryFlag> of ( Integer flags ) {
    EnumSet<SupplierTravelCategoryFlag> result = EnumSet.noneOf(SupplierTravelCategoryFlag.class);

    for ( SupplierTravelCategoryFlag flag : SupplierTravelCategoryFlag.values() ) {
      if ( hasFlag(flags, flag) ) {
        result.add(flag);
      }
    }

    if ( flags != null && flags != 0 ) {
      result.remove(SupplierTravelCategoryFlag.ALL);
    }

    return result;
  }
}
