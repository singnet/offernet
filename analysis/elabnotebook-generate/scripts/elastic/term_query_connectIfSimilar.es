GET /filebeat-*/_search
{
  "_source": ["simulationId", "method", "avg_wallTime_ms", "@timestamp"], 
  "query": {"match": { "method" : "connectIfSimilar" }},
  "aggs": {
    "group_by_SIM": {
      "terms": {
        "field": "simulationId.keyword",
        "order": {
          "avg_wallTime_ms": "desc"
        }
      },
      "aggs": {
        "avg_wallTime_ms": {
          "avg": {
            "field": "wallTime_ms"
          }
        }
      }
    }
  }
}