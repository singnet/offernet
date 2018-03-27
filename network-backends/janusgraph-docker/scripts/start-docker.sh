#!/bin/sh

dir=$(pwd)
docker run -it -p 8182:8182 -p 8184:8184 -v "$dir/offernet-graph/db":/var/lib/janusgraph/db kabirkbr/janusgraph-docker:0.2.0 sh /var/lib/janusgraph/bin/start-janusgraph.sh
