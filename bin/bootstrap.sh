#!/bin/bash
cd "$(dirname "$(readlink -f "${BASH_SOURCE[0]}")")"

# Configure docker host
if [[ -n "$(which boot2docker)" ]]; then
    $(boot2docker shellinit)
    export DOCKER_HOST_IP=$(boot2docker ip)
else
    export DOCKER_HOST_IP=127.0.0.1
fi
