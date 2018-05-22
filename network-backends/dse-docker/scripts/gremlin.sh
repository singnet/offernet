#!/bin/sh

HOST=dse-server.host
PORT=8182
/opt/dse/bin/dse gremlin-console $HOST:$PORT -i network-backends/dse-docker/scripts/offernet.gremlin
