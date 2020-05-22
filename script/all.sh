#!/bin/bash
cd htcc-gateway-service/ && sh gateway.sh
cd ../htcc-employee-service/ && sh employee.sh
cd ../htcc-log-service/ && sh log.sh
cd ../htcc-admin-service/ && sh admin.sh
