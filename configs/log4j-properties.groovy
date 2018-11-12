log4j.rootLogger = "INFO, SIM"

log4j.appender.SIM = "org.apache.log4j.FileAppender"
log4j.appender."SIM.File" = './logs/OfferNet.log'
log4j.appender."SIM.Append"=true
log4j.appender."SIM.layout"="org.apache.log4j.PatternLayout"
log4j.appender."SIM.layout.ConversionPattern"= '%d{ISO8601} [%t] %-5p %c %x : %m%n'
log4j.appender."SIM.Threshold"="INFO"

log4j.appender.DB = "org.apache.log4j.FileAppender"
log4j.appender."DB.File" = './logs/OfferNetVerbose.log'
log4j.appender."DB.Append"=true
log4j.appender."DB.layout"="org.apache.log4j.PatternLayout"
log4j.appender."DB.layout.ConversionPattern"= '%d{ISO8601} [%t] %-5p %c %x : %m%n'
log4j.appender."DB.Threshold"="DEBUG"

log4j.appender.TR = "org.apache.log4j.FileAppender"
log4j.appender."TR.File" = './logs/OfferNetTrace.log'
log4j.appender."TR.Append"=true
log4j.appender."TR.layout"="org.apache.log4j.PatternLayout"
log4j.appender."TR.layout.ConversionPattern"= '%d{ISO8601} [%t] %-5p %c %x : %m%n'
log4j.appender."TR.Threshold"="TRACE"