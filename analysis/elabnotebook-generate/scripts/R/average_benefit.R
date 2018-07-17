rm(list=ls())
fullPath = Sys.getenv('R_fullPath')
setwd(fullPath)
bp.df <- read.table(file = "benefitsPenalties.dat",header=T,sep=',')
conf.df <- read.table(file = "parameters.conf",header=F,sep='=')
v <-as.vector(conf.df[,2])
conf <-as.list(v)
names(conf) <- as.list(as.vector(conf.df[,1]))
numberOfAgents <- as.numeric(as.character(conf.df[conf.df$V1=='numberOfAgents',]$V2))
bp.df$value <- bp.df$value / numberOfAgents
# this should be corrected: find a method somehow to exchange values between R and groovy
b <- subset(bp.df, event=='benefitted')
p <- subset(bp.df, event=='penalized')
b <- b[with(b,order(b$time)),]
p <- p[with(p,order(p$time)),]
b <- within(b, cumm_value <-cumsum(value))
p <- within(p, cumm_value <-cumsum(value))
b$timesecs <- ceiling(b$time * 1e-9)
p$timesecs <- ceiling(p$time * 1e-9)
aggp <- aggregate(p$cumm_value, by=list(p$timesecs),FUN=max)
aggb <- aggregate(b$cumm_value, by=list(b$timesecs),FUN=max)
colnames(aggp) <- c('timesecs','cumvalue')
colnames(aggb) <- c('timesecs','cumvalue')

bp.df <- bp.df[with(bp.df,order(bp.df$time)),]
bp.df <- within(bp.df, cumm_value <-cumsum(value))
bp.df$timesecs <- ceiling(bp.df$time * 1e-9)
aggbp.df <- aggregate(bp.df$cumm_value, by=list(bp.df$timesecs),FUN=max)
colnames(aggbp.df) <- c('timesecs','cumvalue')

library(ggplot2)
averageBenefit <- ggplot() +
  geom_point(data=aggbp.df, aes(x=timesecs, y=cumvalue, color="average benefit")) +
  geom_point(data=aggb, aes(x=timesecs, y=cumvalue, color="benefit")) +
  geom_point(data=aggp, aes(x=timesecs, y=cumvalue, color="penalty")) +
  xlab('Simulation time (seconds)')+
  ylab('Units of benefit/penalty')

aggpt <- aggregate(p$value, by=list(p$timesecs),FUN=sum)
aggbt <- aggregate(b$value, by=list(b$timesecs),FUN=sum)
colnames(aggpt) <- c('timesecs','value')
colnames(aggbt) <- c('timesecs','value')

aggbpt.df <- aggregate(bp.df$value, by=list(bp.df$timesecs),FUN=sum)
colnames(aggbpt.df) <- c('timesecs','value')

averageBenefitPerUnitOfTime <- ggplot() +
  geom_point(data=aggbpt.df, aes(x=timesecs, y=value, color="average benefit"), size=.2) +
  geom_point(data=aggbt, aes(x=timesecs, y=value, color="benefit"), size=.2) +
  geom_point(data=aggpt, aes(x=timesecs, y=value, color="penalty"), size=.2) +
  stat_smooth(method="lm",data=aggpt,aes(x=timesecs, y=value, color="penalty"),se=FALSE, size=2)+
  stat_smooth(method="lm",data=aggbpt.df,aes(x=timesecs, y=value, color="average benefit"),se=FALSE, size=2)+
  stat_smooth(method="lm",data=aggbt,aes(x=timesecs, y=value, color="benefit"),se=FALSE, size=2)+
  xlab('Simulation time (seconds)')+
  ylab('Benefit/penalty per unit of time')
