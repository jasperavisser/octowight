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
