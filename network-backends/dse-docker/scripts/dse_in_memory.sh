#!/bin/sh

cqlsh -u offernet_actor_system -p singoffcass69585 -f ../config/offernet_in_memory.cql
nodetool upgradesstables -a offernet