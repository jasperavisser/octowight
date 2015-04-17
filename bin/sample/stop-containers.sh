#!/bin/bash
cd "$(dirname "$(readlink -f "${BASH_SOURCE[0]}")")"

. ../lib/docker.sh
. constants.sh

removeContainer ${NEW_RESOURCE_DETECTOR_NAME}
removeContainer ${REST_API_NAME}
removeContainer ${ROW_CHANGE_EVENT_CHANNEL_NAME}
