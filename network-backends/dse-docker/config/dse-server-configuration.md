# System configuration for OfferNet(s)

## Installing via OpsCenter 6.5.0
0. change and save somewhere safe cassandra local password;
1. Set SSH credentials to use private key of the local user; add public key of the same user to authenticated_keys file: this allows to for the user to access itself via ssh...;
2. For the node configuration, native-transport-address: 0.0.0.0 and broadcast-native-transport-address: 127.0.0.1;
3. In case running Ubuntu 18.04, which is not yet supported by DSE, add to opscenter.conf:
```
[lifecycle_manager]
disable_platform_check = True
```

All the rest according to https://docs.datastax.com/en/opscenter/6.5/opsc/LCM/opscLCMgetStartedWorkflow.html

## System parameters tuning

Following DSE 6.0 Administration guide:
https://docs.datastax.com/en/pdf/dseadmin60.pdf

Done on on-hetzner.host:
1. TCP settings (p. 70);
2. Set user resource limits (p. 72);
3. Disable swap
4. Check the Java Hugepages setting
5. Set the heap size for optional Java garbage collection in DataStax Enterprise
* Heap settings -- 50% of system memory vial Opscenter / cluster profile jvm settings;

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
* follow https://docs.datastax.com/en/dse/6.0/dse-admin/datastax_enterprise/security/Auth/secCreateRootAccount.html:
* `cqlsh -u cassanra -p cassandra_pass` (use cassandra password that was set during installation of course)
* `CREATE ROLE offernet_actor_system with SUPERUSER = true AND LOGIN = true and PASSWORD = 'singoffcass69585';
EXIT;`

## In-memory cassandra (opotional):

* https://docs.datastax.com/en/dse/5.1/dse-admin/datastax_enterprise/inMemory/usingTables.html
* use script network-backends/scripts/dse_in_memory.sh;


