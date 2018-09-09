#install.packages('plotly')
library(plotly)
LoadToEnvironment <- function(RData, env = new.env()){
  load(RData, env)
  return(env) 
}
all.df <- LoadToEnvironment('~/offernet/docs/original-decentralized-vs-centralized-analysis/R_data/summary_of_all_experiments.Rdata')$summary.df
all.df <- all.df[which(all.df$similarityConnectThreshold != "NA"),]

gg <- ggplotly(
  ggplot(all.df, aes(x=maxDistance, y=sum_wallTime_min_total, color=simulationType)) + 
    geom_point(aes(size=foundCycles, label=agentNumber)) + 
    scale_x_discrete(limits=c("5","10","30")) +
    #xlim(c(0, 0.1)) + 
    #ylim(c(0, 500000)) + 
    labs(y='simulationTime, minutes', 
         x='maxDistance'
    )
)
print(gg)
