# octowight
Just playing around with Scala, Spring, Docker

## Required

Add this line to /etc/init/docker.conf
DOCKER_OPTS='-H tcp://0.0.0.0:4243 -H unix:///var/run/docker.sock'
http://www.virtuallyghetto.com/2014/07/quick-tip-how-to-enable-docker-remote-api.html


## TODO
Choose how to run IT
    Containers for
        PostgreSQL
        Zookeeper
        Kafka broker
        Redis
        Event source
        Event consumer (new resources)
        Event consumer (mark as dirty)
        IT suite (separate maven module, test data, output as junit.xml)
    fig-it.yml

Choose & configure image for
    PostgreSQL (layer on top to initialize database for IT)
        https://registry.hub.docker.com/_/postgres/
        docker run -v "${MY_POSTGRES_DATA}":/var/lib/postgresql/data -d postgres
    Zookeeper
    Kafka broker
    Redis

Event source
    (should be a library, so user can swap in another database?)
    Merge 2 projects (hibernate, scalatest)
    Make IT work with HSQLDB
    Add KafkaConfiguration
    Write to kafka (mocked in unit/IT)

Event consumer (new resources)
    (library, so user can define resource detectors)
    Consume kafka topic
    Detect new resources
    Publish to redis & kafka

Event consumer (mark as dirty)
    (library, so user can define resource detectors)
    Consume kafka topic
    Detect new resources
    Publish to redis & kafka
