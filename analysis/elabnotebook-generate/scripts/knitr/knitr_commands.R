library(knitr);
library(markdown);
opts_knit$set(upload.fun = image_uri);
args <- commandArgs()
dataDir <- args[6]
print('from knitr_commands')
print(dataDir)
knit('../knitr/experiment_report_full.Rmd',output='experiment_report_full.md');
markdownToHTML('experiment_report_full.md', 'experiment_report_full.html')
