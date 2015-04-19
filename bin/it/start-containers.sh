#!/bin/bash
cd "$(dirname "$(readlink -f "${BASH_SOURCE[0]}")")"

DOCKER_REUSE_IT=$1

. ../lib/docker.sh
. names.sh

# Remove containers
bash stop-containers.sh ${DOCKER_REUSE_IT}

# Run containers
[[ -n $(isRunning ${POSTGRES_DATA_REPOSITORY_NAME}) ]] || {
    docker run -d --publish=5432:5432 --name=${POSTGRES_DATA_REPOSITORY_NAME} ${POSTGRES_DATA_REPOSITORY_TAG}
    waitForPostgresToStart ${POSTGRES_DATA_REPOSITORY_NAME}
}
[[ -n $(isRunning ${POSTGRES_RESOURCE_REGISRY_NAME}) ]] || {
    docker run -d --publish=5433:5432 --name=${POSTGRES_RESOURCE_REGISRY_NAME} ${POSTGRES_RESOURCE_REGISRY_TAG}
    waitForPostgresToStart ${POSTGRES_RESOURCE_REGISRY_NAME}
}
[[ -n $(isRunning ${ZOOKEEPER_NAME}) ]] || docker run -d --publish=2181:2181 --name ${ZOOKEEPER_NAME} ${ZOOKEEPER_TAG}
[[ -n $(isRunning ${KAFKA_BROKER_NAME}) ]] ||
docker run -d --publish=9092:9092 --name=${KAFKA_BROKER_NAME} --link=${ZOOKEEPER_NAME}:zk \
    --env KAFKA_ADVERTISED_HOST_NAME=${DOCKER_HOST_IP} \
    --volume /var/run/docker.sock:/var/run/docker.sock ${KAFKA_BROKER_TAG}
