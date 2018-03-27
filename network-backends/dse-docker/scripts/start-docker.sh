#!/bin/sh

# deleting all containers (not very efficient, but needed in order to run a container with the same name)
docker ps -q -a | xargs docker rm

# start dse-server with graph enabled
docker run -e DS_LICENSE=accept --name my-dse -d store/datastax/dse-server:5.1.6 -g
