{ 
  "_source": ["simulationId", "method","wallTime_ms"], 
  "query": {
    "bool": {
      "must": [
        { "match_phrase": {
          "simulationId": "SIM07-17-08-45-U9IRxh"
          }
        }
      ],
      "should": [
        { "match": { "method": "naiveCentralizedCycleSearch" } },
        { "match": { "method": "depthFirstCycleSearch" } },
        { "match": { "method": "decentralizedCycleSearch" } }
      ]
    }
  }
}