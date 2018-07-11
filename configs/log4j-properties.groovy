log4j {
    rootLogger="INFO, FileAppender"
    logger."gbi.Offernet"="INFO, FileAppender"
    additivity."gbi.Offernet"=true
    appender.FileAppender = "org.apache.log4j.FileAppender"
    appender."FileAppender.File"="./logs/OfferNet.log"
    appender."FileAppender.Append"=true
    appender."FileAppender.layout"="org.apache.log4j.PatternLayout"
    appender."FileAppender.layout.ConversionPattern"= "%d{ISO8601} [%t] %-5p %c %x : %m%n"
}
