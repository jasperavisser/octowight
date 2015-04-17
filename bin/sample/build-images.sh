#!/bin/bash
cd "$(dirname "$(readlink -f "${BASH_SOURCE[0]}")")"

# Build artifacts
pushd ../..
mvn clean install -DskipTests

# Build images
pushd row-change-event-channel && mvn docker:build && popd
pushd sample-new-resource-detector && mvn docker:build && popd
pushd sample-rest-api && mvn docker:build && popd
