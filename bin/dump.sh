#!/bin/bash 
 
mvn exec:java \
  --quiet \
  -Dexec.mainClass=com.trestechnologies.api.DumpSchema \
  -Dexec.args=$1 \
  -Dexec.classpathScope=runtime
