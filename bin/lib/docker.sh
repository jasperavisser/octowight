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
