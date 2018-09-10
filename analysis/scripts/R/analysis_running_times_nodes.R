library(ggplot2)
library(ggpubr)
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

exp11.env <- LoadToEnvironment('~/offernet/docs/decentralized-vs-centralized-bookdown/R_data/summary_of_all_experiments.Rdata')
exp12.env <- LoadToEnvironment('~/offernet/docs/additional-centralized-vs-decentralized/R_data/summary_of_all_experiments.Rdata')
exp13.env <- LoadToEnvironment('~/offernet/docs/more-centralized-vs-decentralized/R_data/summary_of_all_experiments.Rdata')
all.df <- exp11.env$summary.df
all.df <- rbind(all.df, exp12.env$summary.df)
all.df <- rbind(all.df, exp13.env$summary.df)
# filtering out experiment which was run on random graphs in order to make results more consistent
all.df <- all.df[which(all.df$experimentId != "EXP08-10-01-33-JYSw5Z" & all.df$experimentId != "EXP08-11-12-58-3BvMSL"), ]
all.df$simulationType_agentNumber <- paste(all.df$simulationType,'-',all.df$agentNumber)
all.df <- all.df[which(all.df$simulationType != "Decentralized"), ]
all.df <- all.df[which(all.df$randomWorksNumberMultiplier == 1), ]
all.df <- all.df[which(all.df$similarityConnectThreshold == 0.99), ]

ggplot(all.df, aes(x=vertices_total, y=sum_wallTime_min_total)) + 
    #geom_point(aes(size=agentNumber)) + 
    geom_smooth(method="loess", se=F) + 
    scale_x_continuous(labels=fancy_scientific) +
    geom_text(aes(label=paste(agentNumber,'\nagents')), size=4) +
    #xlim(c(0, 0.1)) + 
    #ylim(c(0, 500000)) + 
    labs(y = 'simulationTime, minutes',
         x = 'number of vertices in the graph') 
