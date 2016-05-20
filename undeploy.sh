#!/bin/bash

cf t
echo yes | cf d -r contactDataService
echo yes | cf d -r contactWebApp
cf ds p-mysql -f
cf ds p-rabbitmq -f
cf ds contact-service -f
cf ds p-redis -f
cf a
cf s
