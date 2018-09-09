library(elastic)
library(jsonlite)
conn <-connect(es_port = 9200)
template <- '{ 
"query": {
"bool": {
"must": [
{ "match_phrase": {
"experimentId": "%s"
}
}
],
"must_not": [
{ "exists": { "field": "simulationId" } }
]
}
}
}'
  query <- sprintf(template, "EXP09-07-02-54-hATxwM")
  source=c('experimentId','agentNumbers','chainLengths','randomWorksNumberMultipliers','maxDistances','similaritySearchThresholds ', 'similarityConnectThresholds')
  
  
  result <- Search(index="filebeat-*", source=source, body=query, asdf=TRUE, size=1000)$hits$hits
  experiment.df <- result[,-c(1:4)]
  vars=sapply(strsplit(colnames(experiment.df), split='.', fixed=TRUE), function(x) (x[2]))
  colnames(experiment.df) <- vars
  refcols <- c("experimentId", "agentNumbers")
  experiment.df <- experiment.df[, c(refcols, setdiff(names(experiment.df), refcols))]
  experiment.df <- t(experiment.df)
  colnames(experiment.df) <- c('Values')

  
  conn <-connect(es_port = 9200)
  template <- '{ 
  "query": {
  "bool": {
  "must": [
  { "match_phrase": {
  "experimentId": "%s"
  }
  },
  { "match" :{
  "keyword": "simulationParameters"
  }},
  { "exists": { "field": "simulationId" } }
  ]
  }
  }
  }'
  query <- sprintf(template, "EXP09-07-02-54-hATxwM")
  source=c('simulationId','agentNumber','chainLength','randomWorksNumberMultiplier','maxDistance','similaritySearchThreshold','similarityConnectThreshold')
  result <- Search(index="filebeat-*", source=source, body=query, asdf=TRUE, size=1000)$hits$hits
  
  simulations.df <- result[,-c(1:4)]
  vars=sapply(strsplit(colnames(simulations.df), split='.', fixed=TRUE), function(x) (x[2]))
  colnames(simulations.df) <- vars
  refcols <- c("simulationId", "agentNumber")
  simulations.df <- simulations.df[, c(refcols, setdiff(names(simulations.df), refcols))]
  simulations.df <- simulations.df[order(simulations.df$agentNumber, simulations.df$chainLength, simulations.df$maxDistance, simulations.df$simulationId),]
  # getting rid of the simulation data on which the experiment crashed -- gives error...
  simulations.df <- simulations.df[which(simulations.df$simulationId != "SIM09-04-12-38-PuznpM--DV"), ]
  rownames(simulations.df) <- c()