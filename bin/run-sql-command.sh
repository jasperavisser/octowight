#!/bin/bash
cd "$(dirname "$(readlink -f "${BASH_SOURCE[0]}")")"

COMMAND="$1"

. it/names.sh

docker exec -it octowight_sampleDataRepository_1 psql -U postgres postgres -c "${COMMAND}"
