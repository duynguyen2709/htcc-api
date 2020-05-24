#!/bin/bash
docker container rm -f htcc-web-service
sleep 10
docker pull duyna5/htcc-web-service:latest
docker run -p 8206:8206 -d \
        -v "$(pwd)/resources":/resources:ro \
        -v "$(pwd)/log":/log \
        --memory 256m --memory-swap 512m \
        --name htcc-web-service \
        --network="host" \
        -e "TZ=Asia/Ho_Chi_Minh" \
        -e "JAVA_OPTS=-Dspring.profiles.active=production -Dspring.config.location=file:/resources/" \
        duyna5/htcc-web-service
