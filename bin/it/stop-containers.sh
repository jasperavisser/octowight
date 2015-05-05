#!/bin/bash
cd "$(dirname "$(readlink -f "${BASH_SOURCE[0]}")")"

DOCKER_REUSE_IT=$1

. ../lib/docker.sh
. names.sh

if [[ "${DOCKER_REUSE_IT}" == "true" ]]; then
    echo "*** Will not stop containers"
    exit
fi

# Remove containers
removeContainer ${KAFKA_BROKER_NAME}
removeContainer ${MONGO_RESOURCE_REGISRY_NAME}
removeContainer ${POSTGRES_DATA_REPOSITORY_NAME}
removeContainer ${POSTGRES_RESOURCE_REGISRY_NAME}
removeContainer ${ZOOKEEPER_NAME}
