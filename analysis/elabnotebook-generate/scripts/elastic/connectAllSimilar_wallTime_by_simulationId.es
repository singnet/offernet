GET /filebeat-*/_search
{
  "size": 0, 
  "query": {"match": { "method" : "connectAllSimilar" }},
  "aggs": {
    "group_by_SIM": {
      "terms": {
        "field": "simulationId.keyword",
        "order": {
          "sumWallTime_ms": "desc"
        }
      },
      "aggs": {
        "sumWallTime_ms": {
          "sum": {
            "field": "wallTime_ms"
          }
        }
      }
    }
  }
}
