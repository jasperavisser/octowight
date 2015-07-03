#!/bin/bash

export DOCKER_HOST="tcp://0.0.0.0:4243"

# Configure docker host
if which boot2docker  >/dev/null 2>&1; then
    $(boot2docker shellinit)
    export DOCKER_HOST_IP=$(boot2docker ip)
else
    export DOCKER_HOST_IP=$(hostname -I | tr -s " " "\n" | head -n 1)
fi

echo "Docker host is set to $DOCKER_HOST_IP"
