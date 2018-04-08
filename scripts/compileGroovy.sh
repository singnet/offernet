#!/bin/bash

if [ ! -d src/main/java ]; then mkdir -p src/main/java; else rm -r src/main/java/*; fi;
cp src/main/groovy/* src/main/java/
cd src/main/java

#remove all @Grab annotations from the file -- cannot be run by java
#sed -i '/@Grab/d' *.groovy

echo $GROOVY_HOME

#compile
groovyc -cp "/home/kabir/.gradle/*" -encoding utf-8 --indy *.groovy
rm *.groovy
