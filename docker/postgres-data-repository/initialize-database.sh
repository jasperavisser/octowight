#!/bin/bash
cat /docker-entrypoint-initdb.d/initialize-database.sql | gosu postgres postgres --single -jE
