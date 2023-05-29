package com.trestechnologies.api.model;

import com.trestechnologies.api.interfaces.APIContext;

import java.io.IOException;
import java.util.List;

public abstract class BaseModel {
  public static ModelMapper<?> mapper ( ) {
    throw new UnsupportedOperationException("ModelMapper<?> mapper ( ) not implemented.");
  }

  public static Object find ( APIContext context, long recNo ) throws IOException {
    throw new UnsupportedOperationException("Object find ( APIContext context, long recNo ) not implemented.");
  }
  
  public static List<?> search ( APIContext context, Object params ) throws IOException {
    throw new UnsupportedOperationException("List<?> search ( APIContext context, Object params ) not implemented.");
  }
}
