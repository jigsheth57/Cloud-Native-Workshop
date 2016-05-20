#!/bin/bash
# mvn clean install package -D skipTests=true
cf t
echo -n "Validate the space & org, you are currently logged in before continuing!"
read
cf cs p-rabbitmq standard p-rabbitmq
cf cs p-redis shared-vm p-redis
cf p
