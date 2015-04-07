#!/bin/bash
#export DOCKER_HOST=tcp://0.0.0.0:4243
#export DOCKER_HOST_IP="127.0.0.1"
#export DOCKER_TLS_VERIFY="1"

export DOCKER_CERT_PATH="/Users/haploid/.boot2docker/certs/boot2docker-vm"
export DOCKER_HOST="tcp://192.168.59.103:2376"
export DOCKER_HOST_IP="192.168.59.103"
export DOCKER_TLS_VERIFY="1"

# TODO: see http://www.virtuallyghetto.com/2014/07/quick-tip-how-to-enable-docker-remote-api.html

# http://wurstmeister.github.io/kafka-docker/
# http://kafka.apache.org/documentation.html#quickstart
# bash start-kafka-shell.sh ${DOCKER_HOST_IP} ${DOCKER_HOST_IP}:2181

docker pull postgres:9.2
docker pull wurstmeister/kafka:0.8.2.0
docker pull wurstmeister/zookeeper
