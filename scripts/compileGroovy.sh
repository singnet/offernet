#!/bin/bash

if [ ! -d src/java ]; then mkdir -p src/java; else rm -r src/java/*; fi;
cp src/groovy/* src/java/
cd src/java

#remove all @Grab annotations from the file -- cannot be run by java
#sed -i '/@Grab/d' *.groovy

echo $GROOVY_HOME

#compile
groovyc -cp "~/.groovy/gradle/*" -encoding utf-8 *.groovy
rm *.groovy
