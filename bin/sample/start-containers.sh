#!/bin/bash
cd "$(dirname "$(readlink -f "${BASH_SOURCE[0]}")")"

. ../lib/docker.sh
. constants.sh

# Remove containers
bash stop-containers.sh

# Run containers
docker run -d --name ${EVENT_EMITTER_NAME} \
	--link=kafka-broker-it:kafka-broker \
	--link=postgres-it:postgres \
	sample-event-emitter
docker run -d --name ${NEW_RESOURCE_DETECTOR_NAME} \
	--link=kafka-broker-it:kafka-broker \
	--link=postgres-it:postgres \
	--link=redis-it:redis \
	--link=zookeeper-it:zookeeper \
	sample-new-resource-detector
docker run -d --name ${REST_API_NAME} \
	--publish=8080:8080 \
	--link=postgres-it:postgres \
	sample-rest-api
