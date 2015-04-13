#!/bin/bash
READLINK=$(which greadlink readlink | head -n1)
cd "$(dirname "$("${READLINK}" -f "$0")")"

DOCKER_REUSE_IT=$1
[[ "${DOCKER_REUSE_IT}" == "true" ]] && {
    echo "*** Will not stop containers"
    exit
}

function removeContainer {
    local name=$1
    if [[ -n $(docker ps --filter="name=${name}" -aq) ]]; then
        docker rm -vf ${name}
    fi
}

POSTGRES_NAME="postgres-it"
REDIS_NAME="redis-it"
ZOOKEEPER_NAME="zookeeper-it"
KAFKA_BROKER_NAME="kafka-broker-it"

# Remove containers
removeContainer ${POSTGRES_NAME}
removeContainer ${REDIS_NAME}
removeContainer ${KAFKA_BROKER_NAME}
removeContainer ${ZOOKEEPER_NAME}
