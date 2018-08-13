#!/bin/bash

cd docs/decentralized-vs-centralized-bookdown
Rscript -e 'bookdown::render_book("index.Rmd", "bookdown::gitbook")'