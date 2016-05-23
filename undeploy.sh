#!/bin/bash

cf t
echo yes | cf d -r contactDataService-blue
echo yes | cf d -r contactDataService-green
echo yes | cf d -r contactWebApp-blue
echo yes | cf d -r contactWebApp-green
cf ds p-mysql -f
cf ds p-rabbitmq -f
cf ds contact-service -f
cf ds p-redis -f
cf a
cf s
