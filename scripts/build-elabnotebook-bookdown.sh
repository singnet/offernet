#!/bin/bash

# 1: change directory to the desired bookdown source:
cd docs/
# 1: Offernet conceptual documentation -- initial, written before experiments
#cd offernet-docs-bookdown

# 2: Descriptive analysis of the first large badge of experiments, quite a bit of them are buggy and not really eligible for proper analysis; a lot of the experiments here were just incomplete runs that crashed anyway...; 
#cd decentralized-vs-centralized-bookdown

# 3: Descriptive analysis of additional experiments to cover some holes in original experiment's parameter space
#cd additional-centralized-vs-decentralized # for discussion and interpretation of experiments

# 4: same as above, with some additional parameters
#cd more-centralized-vs-decentralized

# 5: Descriptive analysis of only certain experiments that make sense, selected from the 2...
#cd original-decentralized-vs-centralized-analysis

# 6: Analysis of data generated in 2,3,4,5, its integration and comparison and discussion of results;
#cd discussion

# rebuilding all with the correction regarding calculation of total computing time (accounting for methods which log overlapping time due to cross-calling)
# 7: from 5
#cd experiment-1-run-1-corrected
#Rscript -e 'bookdown::render_book("index.Rmd", "bookdown::gitbook")'

# 8: from 3
#cd experiment-1-run-2-corrected
#Rscript -e 'bookdown::render_book("index.Rmd", "bookdown::gitbook")'

# 9: from 4
#cd experiment-1-run-3-corrected
#Rscript -e 'bookdown::render_book("index.Rmd", "bookdown::gitbook")'

# 10: from 2
#cd decentralized-vs-centralized-bookdown-corrected
#Rscript -e 'bookdown::render_book("index.Rmd", "bookdown::gitbook")'

# 12: tidy parameter spaces
#cd experiment-1-run-4
#Rscript -e 'bookdown::render_book("index.Rmd", "bookdown::gitbook")'
# 13: tidy parameter spaces 2
#cd experiment-1-run-5
#Rscript -e 'bookdown::render_book("index.Rmd", "bookdown::gitbook")'
# 13: tidy parameter spaces 3
#cd experiment-1-run-6
#Rscript -e 'bookdown::render_book("index.Rmd", "bookdown::gitbook")'

# 11: from 6 -- corrected and expanded discussion with 12, 13
cd discussion-corrected
Rscript -e 'bookdown::render_book("index.Rmd", "bookdown::gitbook")'

