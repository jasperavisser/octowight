#!/bin/bash
COMMAND="$1"

. it/names.sh

docker exec -it ${POSTGRES_DATA_REPOSITORY_NAME} psql -U postgres postgres -c "${COMMAND}"
