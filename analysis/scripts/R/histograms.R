LoadToEnvironment <- function(RData, env = new.env()){
  load(RData, env)
  return(env) 
}

exp11.env <- LoadToEnvironment('~/offernet/docs/decentralized-vs-centralized-bookdown-corrected/R_data/summary_of_all_experiments.Rdata')
exp12.env <- LoadToEnvironment('~/offernet/docs/experiment-1-run-2-corrected/R_data/summary_of_all_experiments.Rdata')
exp13.env <- LoadToEnvironment('~/offernet/docs/experiment-1-run-3-corrected/R_data/summary_of_all_experiments.Rdata')
exp14.env <- LoadToEnvironment('~/offernet/docs/experiment-1-run-4/R_data/summary_of_all_experiments.Rdata')
exp15.env <- LoadToEnvironment('~/offernet/docs/experiment-1-run-5/R_data/summary_of_all_experiments.Rdata')
all.df <- exp11.env$summary.df
all.df <- rbind(all.df, exp12.env$summary.df)
all.df <- rbind(all.df, exp13.env$summary.df)
all.df <- rbind(all.df, exp14.env$summary.df)
all.df <- rbind(all.df, exp15.env$summary.df)

all.df$maxDistance <- as.numeric(all.df$maxDistance)
all.df$similarityConnectThreshold <- as.numeric(all.df$similarityConnectThreshold)

qplot(chol$AGE, geom="histogram")
hist_agentNumber <- qplot(all.df$agentNumber, geom="histogram")
hist_vertices_total <- qplot(all.df$vertices_total, geom="histogram")
hist_edges_total <- qplot(all.df$edges_total, geom="histogram")
hist_chainLength <- qplot(all.df$chainLength, geom="histogram")
hist_maxDistance <- qplot(all.df$maxDistance, geom="histogram")
hist_similaritySearchThreshold <- qplot(all.df$similaritySearchThreshold, geom="histogram")
hist_similarityConnectThreshold <- qplot(all.df$similarityConnectThreshold, geom="histogram")
hist_randomWorksNumberMultiplier <- qplot(all.df$randomWorksNumberMultiplier, geom="histogram")

library(ggpubr)
figure <- ggarrange(hist_agentNumber,hist_vertices_total,
                    hist_edges_total, hist_chainLength, 
                    hist_maxDistance, hist_similaritySearchThreshold,
                    hist_similarityConnectThreshold, hist_randomWorksNumberMultiplier,
                    nrow=2,
                    ncol=4,
                    common.legend=TRUE,
                    legend='bottom')
annotate_figure(figure, top = text_grob("Histograms of regression parameters", size = 10))

fit <- lm(sum_wallTime_min_total ~ agentNumber + vertices_total + edges_total + factor(simulationType) + chainLength + maxDistance + similaritySearchThreshold + similarityConnectThreshold + randomWorksNumberMultiplier, data=all.df)
stargazer(fitCV,fitDV,fit,type = "html",column.labels = c("Centralized", "Decentralized", "All"))