#!/bin/bash

# Install ElasticSearch FileBeat
wget -qO - https://artifacts.elastic.co/GPG-KEY-elasticsearch | sudo apt-key add -
sudo apt-get -y install apt-transport-https
echo "deb https://artifacts.elastic.co/packages/6.x/apt stable main" | sudo tee -a /etc/apt/sources.list.d/elastic-6.3.1.list
sudo apt-get update && sudo apt-get install filebeat -y
sudo cp ../configs/filebeat.yml /etc/