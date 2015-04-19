#!/bin/bash
cd "$(dirname "$(readlink -f "${BASH_SOURCE[0]}")")"

pushd ..
bash bin/it/build-images.sh \
	&& bash bin/it/start-containers.sh \
	&& bash bin/sample/build-images.sh \
	&& bash bin/sample/start-containers.sh \
	&& watch -n1 docker ps
