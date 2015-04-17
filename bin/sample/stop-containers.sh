#!/bin/bash
READLINK=$(which greadlink readlink | head -n1)
cd "$(dirname "$("${READLINK}" -f "${BASH_SOURCE[0]}")")"

. ../lib/docker.sh
. constants.sh

removeContainer ${NEW_RESOURCE_DETECTOR_NAME}
removeContainer ${REST_API_NAME}
removeContainer ${ROW_CHANGE_EVENT_CHANNEL_NAME}
