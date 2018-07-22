# System configuration for DSE
Following DSE 6.0 Administration guide:
https://docs.datastax.com/en/pdf/dseadmin60.pdf

## Installing via OpsCenter 6.5.0
0. change and save somewhere safe cassandra local password;
1. Set SSH credentials to use private key of the local user; add public key of the same user to authenticated_keys file: this allows to for the user to access itself via ssh...;
2. For the node configuration, native-transport-address: 0.0.0.0 and broadcast-native-transport-address: 127.0.0.1;


All the rest according to https://docs.datastax.com/en/opscenter/6.5/opsc/LCM/opscLCMgetStartedWorkflow.html

## System tuning
(on-hetzner.host)

1. TCP settings (p. 70); - done;
2. Disable CPU frequency scaling -- **not done**; use if CPU becomes a bottleneck: https://support.datastax.com/hc/en-us/articles/115003018063
3. 
