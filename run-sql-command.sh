#!/bin/bash
COMMAND="$1"
docker exec -it postgres-it psql -U postgres postgres -c "${COMMAND}"
