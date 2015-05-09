#!/bin/bash
cd "$(dirname "$(readlink -f "${BASH_SOURCE[0]}")")"

COMMAND="$1"

# TODO: doesn't work with /etc/profile, i think
docker exec -it mongo-resource-registry-it mongo "${COMMAND}"
