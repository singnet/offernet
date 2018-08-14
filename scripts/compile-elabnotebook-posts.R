library(knitr);
opts_knit$set(upload.fun = image_uri);
args <- commandArgs()
setwd('analysis/elabnotebook-generate/scripts/knitr/')
files <- list.files(pattern = "[.]Rmd$")
for (f in files) knit(f);