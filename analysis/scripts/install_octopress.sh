#!/bin/bash

cd $PWD/analysis/elabnotebook-generate/
if  [ ! -d octopress-2.0 ]; then
	wget https://github.com/imathis/octopress/archive/v2.0.tar.gz;
	tar -zxf v2.0.tar.gz;
	rm v2.0.tar.gz;
fi;