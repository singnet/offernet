#!/bin/sh

installDir="$1"
if [ ! -d $installDir ]; then mkdir -p $installDir; fi
cd $installDir
cd $installDir
if [ ! -d titan-1.0.0-hadoop1 ]; then
  if [ ! -f titan-1.0.0-hadoop1.zip ]; then
    wget http://s3.thinkaurelius.com/downloads/titan/titan-1.0.0-hadoop1.zip
  fi
  unzip titan-1.0.0-hadoop1.zip
  rm -r titan-1.0.0-hadoop1.zip
else
  echo "Titan server already exists in the install directory - not installing"
fi
