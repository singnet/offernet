#!/bin/bash



# create elasticsearch persistent data directory

if [ ! -d $PWD/analysis/data/elasticsearch/data ];
	then mkdir -p $PWD/analysis/data/elasticsearch/data; fi; 

# launch elastic stack
cd $PWD/analysis/monitoring-engine/

# log to file
docker-compose up -d 
