#!/bin/bash

# set up dependency directory
DEPEN="./required"	
if [ ! -d $DEPEN ]; then mkdir -p $DEPEN; fi
cd $DEPEN

# Java
if [ ! -d jre ]; then if [ ! -f jre-8u111-linux-x64.tar.gz ]; then wget --no-check-certificate --no-cookies --header "Cookie: oraclelicense=accept-securebackup-cookie" 'http://download.oracle.com/otn-pub/java/jdk/8u111-b14/jre-8u111-linux-x64.tar.gz'; fi; tar xf jre-8u111-linux-x64.tar.gz; rm jre-8u111-linux-x64.tar.gz; mv jre1.8.0_111 jre; fi
# Groovy
if [ ! -d groovy ]; then if [ ! -f groovy-binary-2.4.1.zip ]; then wget -O groovy-binary-2.4.1.zip "https://bintray.com/groovy/maven/download_file?file_path=groovy-binary-2.4.1.zip"; fi; unzip groovy-binary-2.4.1.zip; mv groovy-2.4.1 groovy; rm groovy-binary-2.4.1.zip; fi

