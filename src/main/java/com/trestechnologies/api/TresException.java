package com.trestechnologies.api;

import com.trestechnologies.api.annotation.ExitOnError;

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
      case 109 : return "Cannot clear agency";
      case 500 : return "Not found: " + message;
      case 501 : return "Permission invalid: " + message;
      case 502 : return "No decrypt permission: " + message;
      case 503 : return "SQL error: " + message;
      case 504 : return "Need admin login: " + message;
      default :
        if ( message != null ) { return message; }
        
        return "Unknown root cause (code: " + code + ").";
    }
  }
  
  public TresException ( int code ) {
    this(code, null);
    
    this.code = code;
  }
  
  public TresException ( int code, String message ) {
    super(rootCause(code, message));
    
    this.code = code;
    
    ExitOnError.Evaluator.ifShouldExit(code, annotation -> {
      System.err.println("=== Halted to avoid locking the user out of the system. ===");
      System.err.println("Applicable annotation: " + annotation);
      System.err.println("Applicable error message: " + message);
      
      System.exit(code);
    });
  }
  
  public TresException ( String message ) {
    super(rootCause(-1, message));
  }

  public TresException ( String message, Throwable cause ) {
    super(rootCause(-1, message), cause);
  }

  public int getCode ( ) { return code; }

  public void setCode ( int code ) { this.code = code; }
}
