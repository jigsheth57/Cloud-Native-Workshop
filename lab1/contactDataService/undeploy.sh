#!/bin/bash
CF_APP=${1:-contactDataService}

cf t
echo yes | cf d -r $CF_APP-blue
echo yes | cf d -r $CF_APP-green
cf ds p-mysql -f
cf ds p-rabbitmq -f
cf ds contact-service -f
cf a
cf s
