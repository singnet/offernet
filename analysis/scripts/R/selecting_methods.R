library(elastic)
library(jsonlite)
library(plyr)
library(kableExtra)

conn <-connect(es_port = 9200)
template <- '
{
  "_source": false, 
  "query": {
  "match_phrase": { "simulationId" : "%s" }},
  "aggs": {
  "group_by_method": {
  "terms": {
  "field": "method.keyword"
  },
  "aggs": {
  "group_by_method": {
  "terms": {
  "field": "method.keyword",
  "order": {
  "avg_wallTime_ms": "desc"
  }
  },
  "aggs": {
  "sum_wallTime_ms": {
  "sum": {
  "field": "wallTime_ms"
  }
  },
  "avg_wallTime_ms": {
  "avg": {
  "field": "wallTime_ms"
  }
  }
  }
  }
  }
  }
  }
}      
  '
        simulationId <- 'SIM09-04-11-00-wrFr95--DV'
        query <- sprintf(template, simulationId)
        sim.result <- Search(index="filebeat-*", body=query, asdf=TRUE, size=10000)
        sim.df <- sim.result$aggregations$group_by_method$buckets$group_by_method.buckets
        methods.df <- ldply(sim.df, data.frame)
        if (grepl('–CV',simulationId) | grepl('--CV',simulationId)) {
          # this is centralized simulation therefore we need only these methods:
          methodsToExclude <- c('existsSimilarity', 'connectIfSimilar', 'similarityEdges', 'init')
        } else {
          methodsToExclude <- c('knowsAgent', 'addItemToWork','ownsWork', 'init', 'getWorks')
        }
        methodsInTotal.df <- methods.df[which(!(methods.df$key %in% methodsToExclude)),]
        # no need to include analysis methods in calculation of total time spent -- exluding all
        analysisMethods <- c('degreeDistribution','allEdgesByLabel', 'allVerticesByLabel', 'similarityEdgesByWeight', 'checkFoundPaths', 'pathContainsChain')
        for (analysisMethod in analysisMethods) {
          if (analysisMethod %in% methods.df$key) {
            i <- which(methods.df$key == analysisMethod)
            methods.df <- methods.df[-c(i),]
          }
        }
        conn<-NULL
        func <- function(z) if (is.numeric(z)) sum(z) else ''
        sumrow <- as.data.frame(lapply(methodsInTotal.df, func))      
        sumrow$avg_wallTime_ms.value <- ''
        sumrow$key <- "Total included"
        
        methods.df$includedInTotal <- ifelse(methods.df$key %in% methodsToExclude, "No", "Yes")
        sumrow$includedInTotal <- ''
        
        last_row <- nrow(methods.df) +1
        m <- kable(rbind(methods.df,sumrow),format.args = list(decimal.mark = '.', big.mark = ",")) %>%
          kable_styling(bootstrap_options='condensed',full_width = FALSE, position = 'left') %>%
          row_spec(last_row, bold = TRUE)
        #column_spec(0:ncol(experiment.df),width="10em") 
        #add_header_above(c("labels"=5))
        print(m)
        