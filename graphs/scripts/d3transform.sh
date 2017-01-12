#!/bin/sh

set -x #echo on

cp $1.graphson $1.json
# convert edge definitions from graphson to d3 json
sed -i 's/_label/label/' ./$1.json # change _label to label
sed -i 's/_inV/target/' ./$1.json # change _inV to target
sed -i 's/_outV/source/' ./$1.json # change _outV to source
sed -i 's/vertices/nodes/' ./$1.json # change vertices to nodes
sed -i 's/edges/links/' ./$1.json # change edges to links
sed -i 's/["mode":"NORMAL",]//' ./$1.json # remove 'normal' lines,

