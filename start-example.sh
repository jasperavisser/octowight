#!/bin/bash

function removeContainer {
    local name=$1
    if [[ -n $(docker ps --filter="name=${name}" -aq) ]]; then
        docker rm -vf ${name}
    fi
}

NEW_RESOURCE_DETECTOR_NAME="new-resource-detector-example"
ROW_CHANGE_EVENT_CHANNEL_NAME="row-change-event-channel"

removeContainer ${NEW_RESOURCE_DETECTOR_NAME}
removeContainer ${ROW_CHANGE_EVENT_CHANNEL_NAME}

# TODO: initialize database (otherwise user does not exist)!
# TODO: script to stop containers
# TODO: fix connectivity troubles (probably kafka won't work, because published ip is different)
# TODO: reduce code duplication in bash scripts!

# Run containers
docker run -d --name ${NEW_RESOURCE_DETECTOR_NAME} \
	--link=kafka-broker-it:kafka-broker \
	--link=postgres-it:postgres \
	--link=redis-it:redis \
	--link=zookeeper-it:zookeeper \
	new-resource-detector-example

docker run -d --name ${ROW_CHANGE_EVENT_CHANNEL_NAME} \
	--link=kafka-broker-it:kafka-broker \
	--link=postgres-it:postgres \
	row-change-event-channel
