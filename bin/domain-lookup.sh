#!/bin/bash 
 
mvn exec:java \
  --quiet \
  -Dexec.mainClass=com.trestechnologies.api.DomainLookup \
  -Dexec.args="$*" \
  -Dexec.classpathScope=runtime
