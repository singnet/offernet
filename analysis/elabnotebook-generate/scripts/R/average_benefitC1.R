rm(list=ls())
bp.df <- read.table(file = "/media/ramdisk/benefitsPenalties.dat",header=T,sep=',')
bp.df <- subset(bp.df, cluster=='c1')
numberOfAgents = 20
bp.df$value <- bp.df$value / numberOfAgents 
b <- subset(bp.df, event=='benefitted')
p <- subset(bp.df, event=='penalized')
b <- b[with(b,order(b$time)),]
p <- p[with(p,order(p$time)),]
b <- within(b, cumm_value <-cumsum(value))
p <- within(p, cumm_value <-cumsum(value))
b$timemillis <- ceiling(b$time * 1e-6)
p$timemillis <- ceiling(p$time * 1e-6)
aggp <- aggregate(p$cumm_value, by=list(p$timemillis),FUN=max)
aggb <- aggregate(b$cumm_value, by=list(b$timemillis),FUN=max)
colnames(aggp) <- c('timemillis','value')
colnames(aggb) <- c('timemillis','value')

bp.df <- bp.df[with(bp.df,order(bp.df$time)),]
bp.df <- within(bp.df, cumm_value <-cumsum(value))
bp.df$timemillis <- ceiling(bp.df$time * 1e-6)
aggbp.df <- aggregate(bp.df$cumm_value, by=list(bp.df$timemillis),FUN=max)
colnames(aggbp.df) <- c('timemillis','value')

library(ggplot2)
plotC1 <- ggplot() +
  geom_point(data=aggbp.df, aes(x=timemillis, y=value, color="average benefit")) +
  geom_point(data=aggb, aes(x=timemillis, y=value, color="benefit")) +
  geom_point(data=aggp, aes(x=timemillis, y=value, color="penalty")) +
  xlab('Simulation time (miliseconds)')+
  ylab('Units of benefit/penalty')