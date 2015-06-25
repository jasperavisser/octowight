#!/bin/bash
cd "$(dirname "$(readlink -f "${BASH_SOURCE[0]}")")"
set -e

# Build artifacts
pushd ../..
mvn $1 install -DskipTests
popd

# Build images
pushd ../..
pushd dirty-resource-detector && mvn docker:build && popd
pushd sample-event-emitter && mvn docker:build && popd
pushd sample-new-resource-detector && mvn docker:build && popd
pushd sample-resource-builder && mvn docker:build && popd
pushd sample-rest-api && mvn docker:build && popd
