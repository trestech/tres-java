#!/bin/bash 
 
mvn exec:java \
  --quiet \
  -Dexec.mainClass=com.trestechnologies.api.CBMSListPull \
  -Dexec.args=$1 \
  -Dexec.classpathScope=runtime
