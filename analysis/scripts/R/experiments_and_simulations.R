library(ggplot2)
LoadToEnvironment <- function(RData, env = new.env()){
  load(RData, env)
  return(env) 
}

exp11.env <- LoadToEnvironment('~/offernet/docs/decentralized-vs-centralized-bookdown/R_data/summary_of_all_experiments.Rdata')
exp12.env <- LoadToEnvironment('~/offernet/docs/additional-centralized-vs-decentralized/R_data/summary_of_all_experiments.Rdata')
exp13.env <- LoadToEnvironment('~/offernet/docs/more-centralized-vs-decentralized/R_data/summary_of_all_experiments.Rdata')
all.df <- exp11.env$summary.df
all.df <- rbind(all.df, exp12.env$summary.df)
all.df <- rbind(all.df, exp13.env$summary.df)

run1 <- c(
  'EXP08-02-03-31-H5aVtH',
  'EXP08-05-12-48-qrNbzW',
  'EXP08-07-05-04-95KIBv',
  'EXP08-09-01-35-9owMuE',
  'EXP08-09-05-27-BmCFH6',
  'EXP08-10-01-33-JYSw5Z',
  'EXP08-10-10-15-t7WP6v',
  'EXP08-11-12-38-3dSVwT',
  'EXP08-11-12-44-BoTBgN',
  'EXP08-11-12-54-LgXUNA',
  'EXP08-11-12-58-3BvMSL',
  'EXP08-12-11-17-5tvhCK'
)
run2 <- c('EXP09-04-04-11-kvIbMs','EXP09-04-11-00-zmNPzL','EXP09-04-01-13-AzAjTh')
run3 <- c('EXP09-05-02-15-an9jYp','EXP09-05-11-49-gcWcHs','EXP09-07-02-54-hATxwM')

all.df$run <- ifelse(all.df$experimentId %in% run1,"Run1", ifelse(all.df$experimentId %in% run2,"Run2", "Run3"))

library(dplyr)
byAgentNumber <-
  all.df %>%
  group_by(agentNumber, run) %>%
  summarise(n_distinct(simulationId))
names <- c('agentNumber','run','simulationCount')
colnames(byAgentNumber) <- names

totals <- byAgentNumber %>%
  group_by(run) %>%
  summarise(total = sum(simulationCount))

byAgentNumberPlot <- ggplot(byAgentNumber, aes(x = run, y = simulationCount, fill = agentNumber, label = agentNumber)) +
  geom_bar(stat = "identity") +
  geom_text(size = 4, position = position_stack(vjust = 0.5)) +
  scale_fill_continuous(low="green4", high="brown")+
  geom_text(aes(run, total, label = total, fill = NULL), data = totals, position=position_dodge(width=0.9), vjust=-0.4) +
  labs(title='agentNumber',
      y = 'Simulation count') +
  theme(axis.title.x=element_blank(),
        legend.title=element_blank(),
        plot.title = element_text(size=12))
  
byMaxDistance <-
  all.df %>%
  group_by(maxDistance, run) %>%
  summarise(n_distinct(simulationId))
names <- c('maxDistance','run','simulationCount')
colnames(byMaxDistance) <- names

totals <- byMaxDistance %>%
  group_by(run) %>%
  summarise(total = sum(simulationCount))

byMaxDistancePlot <- ggplot(byMaxDistance, aes(x = run, y = simulationCount, fill = maxDistance, label = maxDistance)) +
  geom_bar(stat = "identity") +
  geom_text(size = 4, position = position_stack(vjust = 0.5)) +
  #scale_fill_continuous(low="green4", high="brown")+
  labs(title='maxDistance',y = 'Simulation count') +
  theme(axis.title.x=element_blank(),
        plot.title = element_text(size=12)) +
  guides(fill=FALSE)

byChainLength <-
  all.df %>%
  group_by(chainLength, run) %>%
  summarise(n_distinct(simulationId))
names <- c('chainLength','run','simulationCount')
colnames(byChainLength) <- names

totals <- byChainLength %>%
  group_by(run) %>%
  summarise(total = sum(simulationCount))

byChainLengthPlot <-ggplot(byChainLength, aes(x = run, y = simulationCount, fill = chainLength, label = chainLength)) +
  geom_bar(stat = "identity") +
  geom_text(size = 4, position = position_stack(vjust = 0.5)) +
  scale_fill_continuous(low="green4", high="blue")+
  labs(title='chainLength',y = 'Simulation count') +
  theme(axis.title.x=element_blank(),
        plot.title = element_text(size=12)) +
  guides(fill=FALSE)

byWorksMultipliers <-
  all.df %>%
  group_by(randomWorksNumberMultiplier, run) %>%
  summarise(n_distinct(simulationId))
names <- c('randomWorksNumberMultiplier','run','simulationCount')
colnames(byWorksMultipliers) <- names

totals <- byWorksMultipliers %>%
  group_by(run) %>%
  summarise(total = sum(simulationCount))

byWorksMultipliersPlot <-ggplot(byWorksMultipliers, aes(x = run, y = simulationCount, fill = randomWorksNumberMultiplier, label = randomWorksNumberMultiplier)) +
  geom_bar(stat = "identity") +
  geom_text(size = 4, position = position_stack(vjust = 0.5)) +
  scale_fill_continuous(low="green", high="orange")+
  labs(title='randomWorksNumberMultiplier',y = 'Simulation count') +
  theme(axis.title.x=element_blank(),
        plot.title = element_text(size=12)) +
  guides(fill=FALSE)

byConnectThreshold <-
  all.df %>%
  group_by(similarityConnectThreshold, run) %>%
  summarise(n_distinct(simulationId))
names <- c('similarityConnectThreshold','run','simulationCount')
colnames(byConnectThreshold) <- names

totals <- byConnectThreshold %>%
  group_by(run) %>%
  summarise(total = sum(simulationCount))

byConnectThresholdPlot <-ggplot(byConnectThreshold, aes(x = run, y = simulationCount, fill = similarityConnectThreshold, label = similarityConnectThreshold)) +
  geom_bar(stat = "identity") +
  geom_text(size = 4, position = position_stack(vjust = 0.5)) +
  labs(title='similarityConnectThreshold',y = 'Simulation count') +
  theme(axis.title.x=element_blank(),
        legend.title=element_blank(),
        plot.title = element_text(size=12))

library(ggpubr)
figure <- ggarrange(byAgentNumberPlot,
                    ggarrange(byMaxDistancePlot,
                              byChainLengthPlot,
                              byWorksMultipliersPlot,
                              nrow=3,
                              ncol=1),
                    byConnectThresholdPlot,
                    nrow=1,
                    ncol=3)
annotate_figure(figure,
                top = text_grob("Simulation counts by key parameters", face = "bold", size = 12))
