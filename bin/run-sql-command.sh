#!/bin/bash
cd "$(dirname "$(readlink -f "${BASH_SOURCE[0]}")")"

COMMAND="$1"

docker exec -it octowight_sampleDataRepository_1 psql -U postgres postgres -c "${COMMAND}"
