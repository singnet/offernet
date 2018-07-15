#!/bin/bash

if [ ! -d ./libs ]; then 
	mkdir -p ./lib; cd ./lib; wget http://central.maven.org/maven2/org/aspectj/aspectjweaver/1.9.1/aspectjweaver-1.9.1.jar;fi;
else if [ ! -f ./libs/aspectjweaver-1.9.1.jar]; then
	cd ./libs; wget http://central.maven.org/maven2/org/aspectj/aspectjweaver/1.9.1/aspectjweaver-1.9.1.jar; fi;
