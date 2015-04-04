#!/bin/bash

# https://github.com/docker/docker/issues/4007
i=5432; VBoxManage modifyvm "boot2docker-vm" --natpf1 "tcp-port$i,tcp,,$i,,$i";

docker build -t "haploid/postgres" postgres
