#!/bin/bash

# Configure docker host
if which boot2docker 2>1 >/dev/null; then
    $(boot2docker shellinit)
    export DOCKER_HOST_IP=$(boot2docker ip)
else
    export DOCKER_HOST_IP=$(hostname -I | tr -s " " "\n" | head -n 1)
fi

echo "Docker host is set to $DOCKER_HOST_IP"
