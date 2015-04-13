#!/bin/bash

SONAR_MYSQL_NAME="sonar-mysql"
SONAR_SERVER_NAME="sonar-server"

# Run containers
docker run -d --publish 3306:3306 --name ${SONAR_MYSQL_NAME} tpires/sonar-mysql
docker run -d --publish 9000:9000 --name ${SONAR_SERVER_NAME} --link ${SONAR_MYSQL_NAME}:db tpires/sonar-server
