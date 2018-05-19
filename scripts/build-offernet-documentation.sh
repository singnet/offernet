#!/bin/bash

cp target/cucumber-json-report.json docs/offernet-docs-bookdown/
cd docs/offernet-docs-bookdown
echo 'processing cucumber-json-report...'
groovy scripts/process_json_report.groovy
echo '...done.'
Rscript -e 'bookdown::render_book("index.Rmd", "bookdown::gitbook")'