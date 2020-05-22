docker exec kafka1 kafka-topics.sh --create --zookeeper 172.17.0.1:2181 --replication-factor 2 --partitions 4 --topic API-LOG &
docker exec kafka1 kafka-topics.sh --create --zookeeper 172.17.0.1:2181 --replication-factor 2 --partitions 4 --topic CHECKIN-LOG &
docker exec kafka1 kafka-topics.sh --create --zookeeper 172.17.0.1:2181 --replication-factor 2 --partitions 4 --topic CHECKOUT-LOG &
docker exec kafka1 kafka-topics.sh --create --zookeeper 172.17.0.1:2181 --replication-factor 2 --partitions 2 --topic COMPLAINT-LOG &
docker exec kafka1 kafka-topics.sh --create --zookeeper 172.17.0.1:2181 --replication-factor 2 --partitions 2 --topic EVENT-CHANGE-LOGIN-STATUS &
docker exec kafka1 kafka-topics.sh --create --zookeeper 172.17.0.1:2181 --replication-factor 2 --partitions 2 --topic EVENT-CREATE-USER &
docker exec kafka1 kafka-topics.sh --create --zookeeper 172.17.0.1:2181 --replication-factor 2 --partitions 4 --topic EVENT-PUSH-NOTIFICATION &
docker exec kafka1 kafka-topics.sh --create --zookeeper 172.17.0.1:2181 --replication-factor 2 --partitions 4 --topic EVENT-READ-NOTIFICATION &
docker exec kafka1 kafka-topics.sh --create --zookeeper 172.17.0.1:2181 --replication-factor 2 --partitions 2 --topic EVENT-UPDATE-COMPANY-USER &
docker exec kafka1 kafka-topics.sh --create --zookeeper 172.17.0.1:2181 --replication-factor 2 --partitions 2 --topic EVENT-UPDATE-EMPLOYEE-INFO &
docker exec kafka1 kafka-topics.sh --create --zookeeper 172.17.0.1:2181 --replication-factor 2 --partitions 2 --topic LEAVING-REQUEST-LOG &
docker exec kafka1 kafka-topics.sh --create --zookeeper 172.17.0.1:2181 --replication-factor 2 --partitions 2 --topic EVENT-ADMIN-SEND-NOTIFICATION &
docker exec kafka1 kafka-topics.sh --create --zookeeper 172.17.0.1:2181 --replication-factor 2 --partitions 2 --topic EVENT-LOAD-ICON &
docker exec kafka1 kafka-topics.sh --create --zookeeper 172.17.0.1:2181 --replication-factor 2 --partitions 2 --topic EVENT-REQUIRE-ICON
