#!/bin/sh

# deleting all containers (not very efficient, but needed in order to run a container with the same name)
docker stop $(docker ps --filter="name=dse" -a -q)
docker ps -q -a | xargs docker rm

# start dse-opscenter
docker run \
	-e DS_LICENSE=accept \
	--name dse-opscenter \
    -d -p 8888:8888 \
    --net=host \
	datastax/dse-opscenter

# start dse-server with graph enabled
docker run \
	-e DS_LICENSE=accept \
	-e LISTEN_ADDRESS=127.0.0.1 \
	-e START_RPC=true \
	--net=host \
	--name dse \
	-d -p 8182:8182 -p 9042:9042 -p 61621:61621 \
	datastax/dse-server -g

# start dse-studio (not needed for tests)
docker run \
	-e DS_LICENSE=accept \
	--name dse-studio \
	-d -p 9091:9091 \
	--net=host \
	datastax/dse-studio

	# -v $PWD/network-backends/persistent/lib/cassandra:/var/lib/cassandra \
	# -v $PWD/network-backends/persistent/log/cassandra/:/var/log/cassandra \

	#-v $PWD/network-backends/persistent/log/spark/:/var/log/spark \
	#-v $PWD/network-backends/config/:/config 
	#-v $PWD/network-backends/persistent/lib/spark/:/var/lib/spark \
	#-v $PWD/network-backends/persistent/lib/dsefs/:/var/lib/dsefs \

	#-v $PWD/network-backends/persistent/config/datastax-studio/:/var/lib/datastax-studio \
