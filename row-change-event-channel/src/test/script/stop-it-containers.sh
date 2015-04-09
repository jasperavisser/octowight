#!/bin/bash
READLINK=$(which readlink greadlink | head -n1)
cd "$(dirname "$("${READLINK}" -f "$0")")"

function removeContainer {
    local name=$1
    if [[ -n $(docker ps --filter="name=${name}" -aq) ]]; then
        docker rm -vf ${name}
    fi
}

POSTGRES_NAME="postgres-it"
ZOOKEEPER_NAME="zookeeper-it"
KAFKA_BROKER_NAME="kafka-broker-it"

# Remove containers
removeContainer ${POSTGRES_NAME}
removeContainer ${KAFKA_BROKER_NAME}
removeContainer ${ZOOKEEPER_NAME}
