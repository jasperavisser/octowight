#!/bin/bash

docker kill zookeeper
docker rm zookeeper
docker run -d -p 2181:2181 \
    --name zookeeper \
    wurstmeister/zookeeper

docker kill kafka-broker
docker rm kafka-broker
docker run -d -p 9092:9092 \
    --env KAFKA_ADVERTISED_HOST_NAME=${DOCKER_HOST_IP} \
    --link zookeeper:zk \
    --volume /var/run/docker.sock:/var/run/docker.sock \
    --name kafka-broker \
    wurstmeister/kafka:0.8.2.0

docker logs -f zookeeper

#docker rm row-change-event-channel
#docker run -d \
#    --link db:db \
#    --name row-change-event-channel \
#    row-change-event-channel
