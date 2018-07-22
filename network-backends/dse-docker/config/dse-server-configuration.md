# System configuration for OfferNet(s)
Following DSE 6.0 Administration guide:
https://docs.datastax.com/en/pdf/dseadmin60.pdf

## Installing via OpsCenter 6.5.0
0. change and save somewhere safe cassandra local password;
1. Set SSH credentials to use private key of the local user; add public key of the same user to authenticated_keys file: this allows to for the user to access itself via ssh...;
2. For the node configuration, native-transport-address: 0.0.0.0 and broadcast-native-transport-address: 127.0.0.1;


All the rest according to https://docs.datastax.com/en/opscenter/6.5/opsc/LCM/opscLCMgetStartedWorkflow.html

## System parameters tuning
(on-hetzner.host)

1. TCP settings (p. 70); - done;
2. Disable CPU frequency scaling -- **not done**; use if CPU becomes a bottleneck: https://support.datastax.com/hc/en-us/articles/115003018063
3. 

## Monitoring-engine

1. Do `docker-compose up > ../../logs/elasticstack.log` in directory ./analysis/monitoring-engine.
2. The elastic search will not start from the first time, because it (somehow) creates persistent data directory outside docker container in ./analysis/data/elasticsearch yet with root owner. change it manually to whoever is running the srcipt: `sudo chmod -R <user>:<user> elasticsearch` 
3. install filebeat with `sh analysis/scripts/install_filebeat.sh` -- may need manual copying of analysis/configs/filebeat.yaml to /etc/filebeat/filebeat.yaml, since filebeat is a system-level service; start by `sudo service filebeat start`;
4. install metricbeat with `sh analysis/scripts/install_metricbeat.sh` -- also may need manual copying of analysis/configs/metricbeat.yml to /etc/metricbeat directory in the host server; start with `sudo service metricbeat start` and check with `sudo service metricbeat status`;

## General java side

1. Add to /etc/hosts file under IPv4 section:
```
127.0.0.1 dse-server.host
127.0.0.1 visualization-server.host
127.0.0.1 monitoring-server.host

```
2. Configure cassandra password to be used by actor system and put it into configs/offernet.conf