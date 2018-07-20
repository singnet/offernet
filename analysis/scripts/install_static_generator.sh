#!/bin/bash

cd $PWD/analysis/elabnotebook-generate/
if  [ ! -d static ]; then
	wget https://github.com/poole/lanyon/archive/v1.0.0.zip;
	unzip v1.0.0.zip;
	mv lanyon-1.0.0 static-generator
	rm v1.0.0.zip;
fi;