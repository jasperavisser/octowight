#!/bin/bash
cd "$(dirname "$(readlink -f "${BASH_SOURCE[0]}")")"

# Pull requisite images
docker pull postgres:9.2
docker pull redis:3.0
docker pull wurstmeister/kafka:0.8.2.0
docker pull wurstmeister/zookeeper

# Build requisite images
pushd ../docker
docker build --tag oracle-java8 oracle-java8
docker build --tag postgres-it postgres-it
docker build --tag postgres-resource-registry postgres-resource-registry
popd
