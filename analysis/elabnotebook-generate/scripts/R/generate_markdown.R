library(knitr);
opts_knit$set(upload.fun = image_uri);
args <- commandArgs()
knit('../knitr/centralized_vs_decentralized_results.Rmd',output='../../tmp/experiment_report.md');

