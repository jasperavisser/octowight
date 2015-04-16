#!/bin/bash

function isRunning {
    local name=$1
    docker ps --filter="name=${name}" -q
}

function removeContainer {
    local name=$1
    if [[ -n $(docker ps --filter="name=${name}" -aq) ]]; then
        docker rm -vf ${name}
    fi
}

function waitForPostgresToStart {
    local name=$1
    docker logs -f ${name} 3>&1 1>&2 2>&3 | {
        while IFS= read -r line; do
            echo "${line}"
            if [[ "${line}" == *"database system is ready to accept connections"* ]]; then
                 pkill -P $$ docker
            fi
        done
    }
}
