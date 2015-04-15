#!/bin/bash
READLINK=$(which greadlink readlink | head -n1)
cd "$(dirname "$("${READLINK}" -f "${BASH_SOURCE[0]}")")"

DOCKER_REUSE_IT=$1

. ../lib/docker.sh
. constants.sh

# Remove containers
bash stop-containers.sh ${DOCKER_REUSE_IT}

# Run containers
[[ -n $(isRunning ${POSTGRES_NAME}) ]] || {
    docker run -d --publish=5432:5432 --name=${POSTGRES_NAME} postgres-it
    WAIT_FOR_POSTGRES_TO_START=true
}
[[ -n $(isRunning ${REDIS_NAME}) ]] || docker run -d --publish=6379:6379 --name=${REDIS_NAME} redis:3.0
[[ -n $(isRunning ${ZOOKEEPER_NAME}) ]] || docker run -d --publish=2181:2181 --name ${ZOOKEEPER_NAME} wurstmeister/zookeeper
[[ -n $(isRunning ${KAFKA_BROKER_NAME}) ]] || docker run -d --publish=9092:9092 --name=${KAFKA_BROKER_NAME} --link=${ZOOKEEPER_NAME}:zk \
    --env KAFKA_ADVERTISED_HOST_NAME=${DOCKER_HOST_IP} \
    --volume /var/run/docker.sock:/var/run/docker.sock wurstmeister/kafka:0.8.2.0

# Wait for postgres to start
if [[ "${WAIT_FOR_POSTGRES_TO_START}" == "true" ]]; then
    docker logs -f ${POSTGRES_NAME} 3>&1 1>&2 2>&3 | {
        while IFS= read -r line; do
            echo "${line}"
            if [[ "${line}" == *"database system is ready to accept connections"* ]]; then
                 pkill -P $$ docker
            fi
        done
    }
fi
