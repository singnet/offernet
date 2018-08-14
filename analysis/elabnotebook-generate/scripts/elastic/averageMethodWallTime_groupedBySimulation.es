GET /filebeat-*/_search
{
  "_source": ["simulationId", "method", "avg_wallTime_ms", "@timestamp", "class"], 
  "query": {"match": { "method" : "pathContainsChain" }},
  "aggs": {
    "group_by_method": {
      "terms": {
        "field": "method.keyword"
      },
      "aggs": {
        "group_by_SIM": {
          "terms": {
            "field": "simulationId.keyword",
            "order": {
              "avg_wallTime_ms": "desc",
              "timestamp_max": "asc"
            }
          },
          "aggs": {
            "avg_wallTime_ms": {
              "avg": {
                "field": "wallTime_ms"
              }
            },
            "timestamp_max": {
              "max": {
                "field": "@timestamp"
              }
            }
          }
        }
      }
    }
  }
}
