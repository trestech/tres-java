package com.trestechnologies.api.interfaces;

import java.util.EnumSet;
import java.util.Set;

/**
 * You can combine multiple area flags to select more than one.  This will
 * produce an integer outside the range of the enum values, which is based
 * on the bit position of the ordinal.  For example, if you want to
 * select both CLIENT and TRAVELER, you would use the following:
 * 
 * <pre><code>
 *   int selectedAreas = AreaFlag.CLIENT.flag | AreaFlag.TRAVELER.flag
 * </code></pre>
 */
public enum AreaFlag implements Flaggable<AreaFlag> {
  ALL, CLIENT, TRAVELER, SUPPLIER, ADVISOR, TRIP, RESERVATION, PAYMENT, ACTIVITY,
  ADVISOR_ADJUSTMENT;
  
  public final int flag;
  
  AreaFlag ( ) { this.flag = 1 << ordinal() - 1; }
  
  @Override
  public int getFlag ( ) { return this.flag; }
  
  @Override
  public Set<AreaFlag> of ( int flags ) {
    EnumSet<AreaFlag> result = EnumSet.noneOf(AreaFlag.class);

    for ( AreaFlag flag : AreaFlag.values() ) {
      if ( Flaggable.hasFlag(flags, flag) ) {
        result.add(flag);
      }
    }

    if ( flags != -1 ) {
      result.remove(AreaFlag.ALL);
    }

    return result;
  }
}
