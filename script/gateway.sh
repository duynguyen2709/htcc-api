#!/bin/bash
docker container rm -f htcc-gateway-service
sleep 2
docker run -p 8761:8761 -d \
        -v "$(pwd)/resources":/conf:ro \
        --name htcc-gateway-service \
        -e "TZ=Asia/Ho_Chi_Minh" \
        -e "JAVA_OPTS=-Xmx128m -Dspring.profiles.active=production-1 -Dspring.config.location=file:/conf/" \
        duyna5/htcc-gateway-service