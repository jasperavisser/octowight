#!/bin/bash

# Configure docker host
if [[ -n "$(which boot2docker)" ]]; then
    $(boot2docker shellinit)
    export DOCKER_HOST_IP=$(boot2docker ip)
else
    export DOCKER_HOST_IP=$(ifconfig eth0 | grep 'inet addr:' | cut -d: -f2 | awk '{ print $1}')
fi
