log4j {
    rootLogger="DEBUG, DebugFileAppender"
    logger."gbi.Offernet"="DEBUG, DebugFileAppender"
    additivity."gbi.Offernet"=true
    appender.WarnFileAppender = "org.apache.log4j.FileAppender"
    appender."DebugFileAppender.File"="./logs/OfferNet.log"
    appender."DebugFileAppender.Append"=true
    appender."DebugFileAppender.layout"="org.apache.log4j.PatternLayout"
    appender."DebugFileAppender.layout.ConversionPattern"= "%-4r %d [%t] %-5p %c %x : %m%n"


    rootLogger="WARN, WarnFileAppender"
    logger."gbi.Offernet"="WARN, WarnFileAppender"
    additivity."gbi.Offernet"=true
    appender.WarnFileAppender = "org.apache.log4j.FileAppender"
    appender."WarnFileAppender.File"="./logs/OfferNet.log"
    appender."WarnFileAppender.Append"=true
    appender."WarnFileAppender.layout"="org.apache.log4j.PatternLayout"
    appender."WarnFileAppender.layout.ConversionPattern"= "%-4r %d [%t] %-5p %c %x : %m%n"


    rootLogger="INFO, InfoFileAppender"
    logger."gbi.Offernet"="INFO, InfoFileAppender"
    additivity."gbi.Offernet"=true
    appender.InfoFileAppender = "org.apache.log4j.FileAppender"
    appender."InfoFileAppender.File"="./logs/OfferNet.log"
    appender."InfoFileAppender.Append"=true
    appender."InfoFileAppender.layout"="org.apache.log4j.PatternLayout"
    appender."InfoFileAppender.layout.ConversionPattern"= "%-4r %d [%t] %-5p %c %x : %m%n"
}
