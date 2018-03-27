#!/bin/sh

if [ ! -d janusgraph ]; 
	then if [ ! -f janusgraph-0.2.0-hadoop2.zip ]; 
		then 
			wget https://github.com/JanusGraph/janusgraph/releases/download/v0.2.0/janusgraph-0.2.0-hadoop2.zip
		fi; 
	unzip janusgraph-0.2.0-hadoop2.zip
	ln -s janusgraph-0.2.0-hadoop2 janusgraph
	rm janusgraph-0.2.0-hadoop2.zip 
fi