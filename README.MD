curl -s 'localhost:8080/actuator/metrics' | jq | grep cache
    "cache.evictions",
    "cache.gets",
    "cache.puts",
    "cache.removals",

curl -s 'localhost:8080/actuator/metrics/cache.gets' | jq  
{
  "name": "cache.gets",
  "description": "The number of times cache lookup methods have returned a cached (hit) or uncached (newly loaded or null) value (miss).",
  "measurements": [
    {
      "statistic": "COUNT",
      "value": 1.0
    }
  ],
  "availableTags": [
    {
      "tag": "result",
      "values": [
        "hit",
        "miss"
      ]
    },
    {
      "tag": "cache.manager",
      "values": [
        "cacheManager"
      ]
    },
    {
      "tag": "cache",
      "values": [
        "helloCache"
      ]
    },
    {
      "tag": "name",
      "values": [
        "helloCache"
      ]
    }
  ]
}



curl -s 'localhost:8080/actuator/metrics/cache.gets?tag=name:helloCache' | jq
{
  "name": "cache.gets",
  "description": "The number of times cache lookup methods have returned a cached (hit) or uncached (newly loaded or null) value (miss).",
  "measurements": [
    {
      "statistic": "COUNT",
      "value": 1.0
    }
  ],
  "availableTags": [
    {
      "tag": "result",
      "values": [
        "hit",
        "miss"
      ]
    },
    {
      "tag": "cache.manager",
      "values": [
        "cacheManager"
      ]
    },
    {
      "tag": "cache",
      "values": [
        "helloCache"
      ]
    }
  ]
}

curl -s 'localhost:8080/actuator/metrics/cache.gets?tag=name:helloCache&tag=result:hit' | jq
{
  "name": "cache.gets",
  "description": "The number of times cache lookup methods have returned a cached (hit) or uncached (newly loaded or null) value (miss).",
  "measurements": [
    {
      "statistic": "COUNT",
      "value": 5.0
    }
  ],
  "availableTags": [
    {
      "tag": "cache.manager",
      "values": [
        "cacheManager"
      ]
    },
    {
      "tag": "cache",
      "values": [
        "helloCache"
      ]
    }
  ]
}
