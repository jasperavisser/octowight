#!/bin/bash
cd "$(dirname "$(readlink -f "${BASH_SOURCE[0]}")")"

. names.sh

# Pull requisite images
docker pull postgres:9.2
docker pull ${KAFKA_BROKER_TAG}
docker pull ${ZOOKEEPER_TAG}

# Build requisite images
pushd ../../docker
docker build --tag oracle-java8 oracle-java8
docker build --tag ${POSTGRES_DATA_REPOSITORY_TAG} postgres-data-repository
docker build --tag ${POSTGRES_RESOURCE_REGISRY_TAG} postgres-resource-registry
popd
