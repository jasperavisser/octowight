#!/bin/bash
cd "$(dirname "$(readlink -f "${BASH_SOURCE[0]}")")"
set -e

# Build requisite images
pushd ../docker
docker build --tag oracle-java8 oracle-java8
docker build --tag kafka-monitor kafka-monitor
popd
