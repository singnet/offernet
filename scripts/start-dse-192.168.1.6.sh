#!/bin/sh

ssh 192.168.1.6
cd /media/data/opt/
dse-5.0.5/bin/dse cassandra -g
datastax-agent-6.0.7/bin/datastax-agent
exit;
/opt/opscenter-6.0.7/bin/opscenter
/opt/datastax-studio-1.0.2/bin/server.sh
