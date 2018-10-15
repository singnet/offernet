#!/bin/bash

#Rscript scripts/compile-elabnotebook-posts.R
#cp analysis/elabnotebook-generate/scripts/knitr/*.md docs/elabnotebook-jekyll/_posts/
cd docs/elabnotebook-jekyll
jekyll build
cd ..
rm -r ./public/elabnotebook
cp -r elabnotebook-jekyll/_site public/
mv public/_site public/elabnotebook
