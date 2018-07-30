GET /filebeat-*/_search
{ 
  "_source": ["simulationId", "method","wallTime_ms", "keyword"], 
  "query": {
    "bool": {
      "must": [
        { "match_phrase": {
          "simulationId": "SIM07-30-03-07-GwqHsU"
          }
        },
        { "bool": {
          "should": [ 
            { "match": { "keyword": "foundPath" } },
            { "match": { "keyword": "foundCycle" } }
          ],
          "minimum_should_match" : 1,
          "boost" : 2.0
        }} 
      ]
    }
  }
}
