#install.packages('elastic')
#install.packages('jsonlite')
library(elastic)
library(jsonlite)
conn <-connect(es_port = 9200)
template <- '{ 
  "query": {
    "bool": {
      "must": [
        { "match_phrase": {
          "experimentId": "EXP08-11-12-58-3BvMSL"
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
source=c('experimentId','simulationId','agentNumber','chainLength','randomWorksNumberMultiplier','maxDistance','similaritySearchThreshold')

template2 <- '{ 
  "query": {
    "bool": {
      "must": [
        { "match_phrase": {
          "experimentId": "EXP08-11-12-58-3BvMSL"
          }
        }
      ],
      "must_not": [
        { "exists": { "field": "simulationId" } }
      ]
    }
  }
}'
source2=c('experimentId','agentNumbers','chainLengths','randomWorksNumberMultipliers','maxDistances','similaritySearchThresholds')

query <- sprintf(template2)
simulations.df <- Search(index="filebeat-*", source=source2, body=query, asdf=TRUE, size=1000)$hits$hits

template3 <- '{    "query": {
        "bool": {
          "must": [
            {"exists" : { "field" : "method" }},
            {"exists" : { "field" : "simulationId" }},
            {"match_phrase": {
              "simulationId": "SIM08-11-01-01-9svHYu--DV"
            }},
            { "bool": {
              "should": [
                  {"match_phrase": {
                    "method": "degreeDistribution"
                  }},
                  {"match_phrase": {
                    "method": "allEdgesByLabel"
                  }},
                  {"match_phrase": {
                    "method": "allVerticesByLabel"
                  }},
                  {"match_phrase": {
                    "method": "similarityEdgesByWeight"
                  }}
              ]
            }}
          ]
        }
    }}'
source3=c('simulationId','results', 'message', 'method','edgeLabel','vertexType','direction')

query <- sprintf(template3)
analysis.df <- Search(index="filebeat-*", source=source3, body=query, asdf=TRUE, size=1000)$hits$hits

