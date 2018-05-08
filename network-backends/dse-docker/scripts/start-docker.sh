#!/bin/sh

# deleting all containers (not very efficient, but needed in order to run a container with the same name)
docker stop $(docker ps -a -q)
docker ps -q -a | xargs docker rm

# start dse-server with graph enabled
docker run -e DS_LICENSE=accept -e LISTEN_ADDRESS=127.0.0.1 -e START_RPC=true --name dse -d -p 8182:8182 -p 9042:9042 datastax/dse-server:5.1.6 -g

# start dse-studio (not needed for tests)
docker run -e DS_LICENSE=accept --name dse-studio -d -p 9091:9091 --link dse kabirkbr/dse-studio:working

# start dse-opscenter
# docker run -e DS_LICENSE=accept --name opscenter -d -p 8888:8888 datastax/dse-opscenter
