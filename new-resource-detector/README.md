# TODO
* IT: Container for zookeeper, kafka, redis, postgres
* KafkaConsumer reads events (1 or block?)
* If block, then group by type
* For each event group, depending on type, invoke some new resource detectors
* Detectors query postgres to see which rowIds represent a resource
* For each resource, if it is not already in redis
    * Get next resource id from redis (INCR)
    * Push to redis (resourceType, resourceId, rowId, timestamp)
    * Push to kafka (resourceType, resourceId) 

## TODO (detailed)
RedisConfiguration
EventConsumerService
ResourceDetectService 
    -> multiple ResourceDetectors, probably a hashmap of sets of detectors per type

    maybe a @Scheduled method that:
        call consumeMultipleEvents
        group by type
        query for new resources per type
        publish resources to redis
        commit Offsets

## TODO (experimental)
Maybe spring integration anyway?
Feature toggle whether to spin down containers