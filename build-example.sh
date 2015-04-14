#!/bin/bash
READLINK=$(which greadlink readlink | head -n1)
cd "$(dirname "$("${READLINK}" -f "$0")")"

# Build artifacts
mvn -Ddocker.reuse.it=true clean install

# Build images
pushd row-change-event-channel && mvn docker:build && popd
pushd new-resource-detector-example && mvn docker:build && popd

