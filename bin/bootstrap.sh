#!/bin/bash

# Configure docker host
if [[ -n "$(which boot2docker)" ]]; then
    $(boot2docker shellinit)
    export DOCKER_HOST_IP=$(boot2docker ip)
else
    export DOCKER_HOST_IP=$(ip -f inet -o addr show eth0 | cut -d\ -f 7 | cut -d/ -f 1)
fi
