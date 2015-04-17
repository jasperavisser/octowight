#!/bin/bash
cd "$(dirname "$(readlink -f "${BASH_SOURCE[0]}")")"

# TODO: need this?
#export DOCKER_HOST=tcp://0.0.0.0:4243
#export DOCKER_HOST_IP="127.0.0.1"
#export DOCKER_TLS_VERIFY="1"

# Configure environment for boot2docker
if [[ -n "$(which boot2docker)" ]]; then
    $(boot2docker shellinit)
    export DOCKER_HOST_IP=$(boot2docker ip)
fi

# Pull requisite images
docker pull postgres:9.2
docker pull redis:3.0
docker pull wurstmeister/kafka:0.8.2.0
docker pull wurstmeister/zookeeper

# Build requisite images
pushd docker
docker build --tag oracle-java8 oracle-java8
docker build --tag postgres-it postgres-it
popd
