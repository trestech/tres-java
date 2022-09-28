package com.trestechnologies.api;

public class TresException extends RuntimeException {
  private int code = -1;
  
  private static String rootCause ( int code, String message ) {
    switch ( code ) {
      case 100 : return "Invalid AppServer client version";
      case 101 : return "Invalid Username/Password";
      case 102 : return "Password is expired";
      case 103 : return "User locked";
      case 104 : return "User disabled";
      case 105 : return "Invalid alias";
      case 106 : return "Agency not initialized";
      case 107 : return "Agency disabled";
      case 108 : return "Agency cancelled";
      default :
        if ( message != null ) { return message; }
        
        return "Unknown root cause (code: " + code + ").";
    }
  }

  public TresException ( int code ) {
    this(code, null);
  }
  
  public TresException ( int code, String message ) {
    super(rootCause(code, message));
  }
  
  public TresException ( String message ) {
    super(rootCause(-1, message));
  }

  public TresException ( String message, Throwable cause ) {
    super(rootCause(-1, message), cause);
  }
}
