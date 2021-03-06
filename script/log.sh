#!/bin/bash
docker container rm -f htcc-log-service
sleep 10
docker pull duyna5/htcc-log-service:latest
docker run -p 8204:8204 -d \
        -v "$(pwd)/resources":/conf:ro \
        -v "$(pwd)/log":/log \
        --memory 256m --memory-swap 512m \
        --add-host=eureka2:10.25.96.4 --add-host=eureka1:127.0.0.1 \
        --name htcc-log-service \
        --network="host" \
        -e "TZ=Asia/Ho_Chi_Minh" \
        -e "JAVA_OPTS=-Dspring.profiles.active=production-1 -Dspring.config.location=file:/conf/" \
        duyna5/htcc-log-service
