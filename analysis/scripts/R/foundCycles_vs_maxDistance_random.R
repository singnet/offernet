LoadToEnvironment <- function(RData, env = new.env()){
  load(RData, env)
  return(env) 
}

fancy_scientific <- function(l) {
  # turn in to character string in scientific notation
  l <- format(l, scientific = TRUE)
  # quote the part before the exponent to keep all the digits
  l <- gsub("^(.*)e", "'\\1'e", l)
  # turn the 'e+' into plotmath format
  l <- gsub("e", "%*%10^", l)
  # return this as an expression
  parse(text=l)
}

exp11.env <- LoadToEnvironment('~/offernet/docs/original-decentralized-vs-centralized-analysis/R_data/summary_of_all_experiments.Rdata')
all.df <- exp11.env$summary.df
all.df$graphType <- ifelse(all.df$experimentId == "EXP08-12-11-17-5tvhCK","smallWorld", "random")

library(dplyr)
deps <- group_by(all.df, maxDistance, graphType)
found <- function(v) {
  ifelse(v>0,1,0)
}

averages <- summarize(deps, success = mean(found(foundCycles)), count = n())

library(ggplot2)
ggplot(data = averages, aes(x=maxDistance, y=graphType, fill=success)) + 
  scale_x_discrete(limits=c("5","10","30")) +
  geom_tile() +
  geom_text(aes(label=paste('cyclesFound: ',scales::percent(round(success,2)),'\n','simulations: ',count)),size=4,na.rm=TRUE,fontface='italic', color='grey15') +
  scale_fill_gradient(low = "firebrick3", high = "springgreen3")
