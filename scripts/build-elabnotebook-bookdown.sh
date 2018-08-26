#!/bin/bash

#cd docs/decentralized-vs-centralized-bookdown #for experiment No1
cd docs/discussion # for discussion and interpretation of experiments
Rscript -e 'bookdown::render_book("index.Rmd", "bookdown::gitbook")'