#!/bin/bash
READLINK=$(which greadlink readlink | head -n1)
cd "$(dirname "$("${READLINK}" -f "${BASH_SOURCE[0]}")")"

DOCKER_REUSE_IT=$1

. ../lib/docker.sh
. constants.sh

if [[ "${DOCKER_REUSE_IT}" == "true" ]]; then
    echo "*** Will not stop containers"
    exit
fi

# Remove containers
removeContainer ${POSTGRES_NAME}
removeContainer ${REDIS_NAME}
removeContainer ${KAFKA_BROKER_NAME}
removeContainer ${ZOOKEEPER_NAME}
