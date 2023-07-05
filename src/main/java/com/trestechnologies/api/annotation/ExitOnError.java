package com.trestechnologies.api.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;
import java.util.function.Consumer;

/**
 * This annotation will allow things like unit test to tell the context to
 * handle certain exceptions differently.  The default behavior is to JVM exit
 * using <code>System.exit(code)</code> on codes 101, 102, 103, and 104.  This
 * annotation will allow you to override that behavior.
 * 
 * This allows tests to fail fast when there's a configuration problem and avoid
 * locking the users out of the system.  It might also come in handy for certain
 * command-line implementations for the same reason.
 * 
 * Typically, to use this annotation, you would do something like this:
 * 
 * <pre>
 *   <code>
 *     @ExitOnError
 *     public class MyClass {
 *       @ExitOnError(exceptCodes = {102})
 *       public void myMethod1 ( ) {
 *         // This method will exit on 101, 103, and 104, but not 102.
 *       }
 *       
 *       public void myMethod2 ( ) {
 *         // This method will exit on 101, 102, 103, and 104.
 *       }
 *     }
 *   </code>
 * </pre>
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ExitOnError {
  int[] onlyCodes () default {101, 102, 103, 104};

  int[] exceptCodes () default {};
  
  class Evaluator {
    public static void ifShouldExit ( int code, Consumer<ExitOnError> consumer ) {
      ExitOnError classScopeAnnotation = null, annotation = null;
      boolean onlyCodeMatch = false, exceptCodeMatch = false;
      
      for ( StackTraceElement caller : Thread.currentThread().getStackTrace() ) {
        String className = caller.getClassName(), methodName = caller.getMethodName();
        
        try {
          Class<?> callerClass = Class.forName(className);
          
          if ( classScopeAnnotation == null ) {
            classScopeAnnotation = callerClass.getAnnotation(ExitOnError.class);
          }
          
          for ( Method m : callerClass.getDeclaredMethods() ) {
            if ( !m.getName().equals(methodName) ) { continue; }
            if ( !m.isAnnotationPresent(ExitOnError.class) ) { continue; }
            
            annotation = m.getAnnotation(ExitOnError.class);
            
            break;
          }
        } catch ( ClassNotFoundException e ) {
          throw new RuntimeException("Could not inspect for annotations.", e);
        }
      }
      
      if ( annotation == null ) { annotation = classScopeAnnotation; }
     
      if ( annotation == null ) { return; }
      
      for ( int onlyCode : annotation.onlyCodes() ) {
        if ( onlyCode == code ) {
          onlyCodeMatch = true;
          
          break;
        }
      }
      
      for ( int exceptCode : annotation.exceptCodes() ) {
        if ( exceptCode == code ) {
          exceptCodeMatch = true;
          
          break;
        }
      }
      
      if ( onlyCodeMatch && !exceptCodeMatch ) {
        consumer.accept(annotation);
      }
    }
  }
}
