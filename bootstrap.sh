#!/bin/bash
READLINK=$(which greadlink readlink | head -n1)
cd "$(dirname "$("${READLINK}" -f "$0")")"

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
popd

# TODO: notes
# http://www.virtuallyghetto.com/2014/07/quick-tip-how-to-enable-docker-remote-api.html
# https://github.com/wouterd/docker-maven-plugin
# http://wurstmeister.github.io/kafka-docker/
# http://kafka.apache.org/documentation.html#quickstart
# bash start-kafka-shell.sh ${DOCKER_HOST_IP} ${DOCKER_HOST_IP}:2181
# http://stackoverflow.com/questions/24705877/cant-get-maven-to-recognize-java-1-8
