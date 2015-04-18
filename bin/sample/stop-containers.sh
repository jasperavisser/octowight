#!/bin/bash
cd "$(dirname "$(readlink -f "${BASH_SOURCE[0]}")")"

. ../lib/docker.sh
. constants.sh

removeContainer ${EVENT_EMITTER_NAME}
removeContainer ${NEW_RESOURCE_DETECTOR_NAME}
removeContainer ${REST_API_NAME}
