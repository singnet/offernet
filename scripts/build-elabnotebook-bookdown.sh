#!/bin/bash

# 1: change directory to the desired bookdown source:

#cd docs/offernet-docs-bookdown
#cd docs/decentralized-vs-centralized-bookdown #for experiment No1
#cd docs/additional-centralized-vs-decentralized # for discussion and interpretation of experiments
#cd docs/more-centralized-vs-decentralized # for discussion and interpretation of experiments
cd docs/discussion # for discussion and interpretation of experiments
#cd docs/original-decentralized-vs-centralized-analysis


# 2: run the script
Rscript -e 'bookdown::render_book("index.Rmd", "bookdown::gitbook")'