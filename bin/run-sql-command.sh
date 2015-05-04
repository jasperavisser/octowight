#!/bin/bash
cd "$(dirname "$(readlink -f "${BASH_SOURCE[0]}")")"

COMMAND="$1"

. it/names.sh

docker exec -it ${POSTGRES_DATA_REPOSITORY_NAME} psql -U postgres postgres -c "${COMMAND}"
