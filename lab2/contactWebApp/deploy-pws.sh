#!/bin/bash
# mvn clean install package -D skipTests=true
cf t
echo -n "Validate the space & org, you are currently logged in before continuing!"
read
cf cs cloudamqp lemur p-rabbitmq
cf cs rediscloud 30mb p-redis
cf p
