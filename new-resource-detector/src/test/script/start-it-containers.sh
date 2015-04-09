#!/bin/bash
READLINK=$(which readlink greadlink | head -n1)
cd "$(dirname "$("${READLINK}" -f "$0")")"

POSTGRES_NAME="postgres-it"
ZOOKEEPER_NAME="zookeeper-it"
KAFKA_BROKER_NAME="kafka-broker-it"

# Remove containers
bash stop-it-containers.sh

# Run containers
docker run -d --publish=5432:5432 --name=${POSTGRES_NAME} postgres:9.2
docker run -d --publish=2181:2181 --name ${ZOOKEEPER_NAME} wurstmeister/zookeeper
docker run -d --publish=9092:9092 --name=${KAFKA_BROKER_NAME} --link=${ZOOKEEPER_NAME}:zk \
    --env KAFKA_ADVERTISED_HOST_NAME=${DOCKER_HOST_IP} \
    --volume /var/run/docker.sock:/var/run/docker.sock wurstmeister/kafka:0.8.2.0

# Wait for postgres to start
docker logs -f ${POSTGRES_NAME} 3>&1 1>&2 2>&3 | {
    while IFS= read -r line; do
        echo "${line}"
        if [[ "${line}" == *"database system is ready to accept connections"* ]]; then
             pkill -P $$ docker
        fi
    done
}
