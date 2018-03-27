FROM nimmis/alpine-java:openjdk-8-jdk
MAINTAINER Kabir Veitas "kabir@singularitynet.io"

COPY janusgraph /var/lib/janusgraph
COPY conf/socket-gremlin-server.yaml /var/lib/janusgraph/conf/gremlin-server/socket-gremlin-server.yaml
COPY conf/socket-janusgraph-berkeleyje-server.properties /var/lib/janusgraph/conf/gremlin-server/socket-janusgraph-berkeleyje-server.properties
COPY bin/start-janusgraph.sh /var/lib/janusgraph/bin/start-janusgraph.sh

# for the bash
#RUN rm /bin/sh && ln -s /bin/bash /bin/sh
RUN apk add --update bash && rm -rf /var/cache/apk/*

RUN mkdir /var/lib/janusgraph/db

EXPOSE 8182
EXPOSE 8184
