#!/bin/bash
cd "$(dirname "$(readlink -f "${BASH_SOURCE[0]}")")"

. ../lib/docker.sh
. names.sh
. ../it/names.sh

# Remove containers
bash stop-containers.sh

# Run containers
docker run -d --name ${EVENT_EMITTER_NAME} \
	--link=${KAFKA_BROKER_NAME}:kafka-broker \
	--link=${POSTGRES_DATA_REPOSITORY_NAME}:postgres-data-repository \
	sample-event-emitter
docker run -d --name ${NEW_RESOURCE_DETECTOR_NAME} \
	--link=${KAFKA_BROKER_NAME}:kafka-broker \
	--link=${POSTGRES_DATA_REPOSITORY_NAME}:postgres-data-repository \
	--link=${POSTGRES_RESOURCE_REGISRY_NAME}:postgres-resource-registry \
	--link=${ZOOKEEPER_NAME}:zookeeper \
	sample-new-resource-detector
docker run -d --name ${REST_API_NAME} \
	--publish=8080:8080 \
	--link=${POSTGRES_DATA_REPOSITORY_NAME}:postgres-data-repository \
	--link=${POSTGRES_RESOURCE_REGISRY_NAME}:postgres-resource-registry \
	sample-rest-api
