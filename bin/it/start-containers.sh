#!/bin/bash
cd "$(dirname "$(readlink -f "${BASH_SOURCE[0]}")")"

DOCKER_REUSE_IT=$1

. ../lib/docker.sh
. constants.sh

# Remove containers
bash stop-containers.sh ${DOCKER_REUSE_IT}

# Run containers
# TODO: map ports in a higher range, so as not to conflict with people running stuff locally
[[ -n $(isRunning ${POSTGRES_NAME}) ]] || {
    docker run -d --publish=5432:5432 --name=${POSTGRES_NAME} postgres-it
    waitForPostgresToStart ${POSTGRES_NAME}
[[ -n $(isRunning ${POSTGRES_RESOURCE_REGISRY_NAME}) ]] || {
    docker run -d --publish=5433:5432 --name=${POSTGRES_RESOURCE_REGISRY_NAME} postgres-resource-registry
    waitForPostgresToStart ${POSTGRES_RESOURCE_REGISRY_NAME}
}
# TODO: redis is not needed anymore
[[ -n $(isRunning ${REDIS_NAME}) ]] || docker run -d --publish=6379:6379 --name=${REDIS_NAME} redis:3.0
[[ -n $(isRunning ${ZOOKEEPER_NAME}) ]] || docker run -d --publish=2181:2181 --name ${ZOOKEEPER_NAME} wurstmeister/zookeeper
[[ -n $(isRunning ${KAFKA_BROKER_NAME}) ]] || docker run -d --publish=9092:9092 --name=${KAFKA_BROKER_NAME} --link=${ZOOKEEPER_NAME}:zk \
    --env KAFKA_ADVERTISED_HOST_NAME=${DOCKER_HOST_IP} \
    --volume /var/run/docker.sock:/var/run/docker.sock wurstmeister/kafka:0.8.2.0
