package com.trestechnologies.api.interfaces;

import java.util.Set;

public interface Flaggable<F> {
  int getFlag( );
  
  static boolean hasFlag ( int flags, Flaggable<?> flag ) {
    return (flags & flag.getFlag()) == flag.getFlag();
  }

  Set<F> of ( int flags );
}