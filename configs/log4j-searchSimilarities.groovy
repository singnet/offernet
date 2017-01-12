log4j {
    rootLogger="INFO, OffernetFileAppender"
    logger."gbi.Offernet"="INFO, OffernetFileAppender"
    additivity."gbi.Offernet"=false
    appender.OffernetFileAppender = "org.apache.log4j.FileAppender"
    appender."OffernetFileAppender.File"="./logs/searchSimilarites.log"
    appender."OffernetFileAppender.Append"=false
    appender."OffernetFileAppender.layout"="org.apache.log4j.PatternLayout"
    appender."OffernetFileAppender.layout.ConversionPattern"= "%-4r %d [%t] %-5p %c %x : %m%n"
}
