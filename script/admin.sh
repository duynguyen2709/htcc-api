#!/bin/bash
docker container rm -f htcc-admin-service
sleep 20
docker pull duyna5/htcc-admin-service:latest
docker run -p 8202:8202 -d \
        -v "$(pwd)/resources":/conf:ro \
        --add-host=eureka2:10.25.96.4 --add-host=eureka1:127.0.0.1 \
        --name htcc-admin-service \
        --network="host" \
        -e "TZ=Asia/Ho_Chi_Minh" \
        -e "JAVA_OPTS=-Xmx256m -Dspring.profiles.active=production-1 -Dspring.config.location=file:/conf/" \
        duyna5/htcc-admin-service