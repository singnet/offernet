#!/bin/bash

# all DSE (dse-server, dse-opscenter, dse-studio)
sh network-backends/dse-docker/scripts/start-docker.sh

# all elastic (elasticsearch,kibana, logstash)
sh analysis/scripts/start_elastic.sh

# beats
sh analysis/scripts/start_filebeat.sh
sh analysis/scripts/start_metricbeat.sh