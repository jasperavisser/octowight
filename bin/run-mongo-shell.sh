#!/usr/bin/env bash
docker run -it --rm \
    --link octowightexample_resourceRegistry_1:mongohost \
    --entrypoint "/usr/bin/mongo" \
    mongo \
    --host mongohost resource-registry