#!/bin/bash
docker rm zookeeper
docker run -d -p 2181:2181 \
    --name zookeeper \
    jplock/zookeeper

docker rm kafka-broker
docker run -d -p 9092:9092 \
    --env BROKER_ID=1 \
    --env HOST_IP=192.168.1.128 \
    --env PORT=9092 \
    --link zookeeper:zk \
    --name kafka-broker \
    wurstmeister/kafka:0.8.1

docker rm row-change-event-channel
docker run -d \
    --link db:db \
    --name row-change-event-channel \
    row-change-event-channel
