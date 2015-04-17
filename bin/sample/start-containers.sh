#!/bin/bash
READLINK=$(which greadlink readlink | head -n1)
cd "$(dirname "$("${READLINK}" -f "${BASH_SOURCE[0]}")")"

. ../lib/docker.sh
. constants.sh

# Remove containers
bash stop-containers.sh

# Run containers
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
docker run -d --name ${ROW_CHANGE_EVENT_CHANNEL_NAME} \
	--link=kafka-broker-it:kafka-broker \
	--link=postgres-it:postgres \
	row-change-event-channel
