GET /filebeat-*/_search
{
    "query": {
        "bool": {
          "must": [
            {"exists" : { "field" : "experimentId" }}
          ],
          "must_not": [
            {"exists" : { "field" : "simulationId"}}
          ]
        }
    },
    "sort": [
      {
        "@timestamp": {
          "order": "desc"
        }
      }
    ]
}