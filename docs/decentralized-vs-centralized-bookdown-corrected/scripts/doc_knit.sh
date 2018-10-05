#!/bin/bash

export PATH=/opt/yspaceship/required/node/bin/:$PATH

DIR_RMD=.
DIR_MD=$DIR_RMD/_knitted
DIR_SC=$DIR_RMD/_temp/screenshots
rm -r $DIR_MD; mkdir -p $DIR_MD 
rm -r $DIR_SC; mkdir -p $DIR_SC

for file in $DIR_RMD/*.Rmd
do
	filename=$(basename "$file")
	extension="${filename##*.}"
	filename="${filename%.*}"

	echo "Processing $file..."
	Rscript -e "library(knitr); wd<-'$PWD/$DIR_RMD'; knit('$file',output='$DIR_MD/$filename.md')"
done

cd $DIR_MD
cp ../book.json .
gitbook install
gitbook build

cd _book
mkdir images 
cp ../../_temp/screenshots/*.gif images/ 