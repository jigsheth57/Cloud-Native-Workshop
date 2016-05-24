#!/bin/bash
CF_APP='contactWebApp'
CF_APPS_DOMAIN='local.pcfdev.io'

mvn clean install package -D skipTests
if [ "$?" -ne "0" ]; then
  exit $?
fi

DEPLOYED_VERSION_CMD=$(CF_COLOR=false cf a | grep $CF_APP- | cut -d" " -f1| cut -d"-" -f2)
DEPLOYED_VERSION="$DEPLOYED_VERSION_CMD"
echo "Deployed Version: $DEPLOYED_VERSION"
CURRENT_VERSION="blue"
if [ ! -z "$DEPLOYED_VERSION" -a "$DEPLOYED_VERSION" == "blue" ]; then
  CURRENT_VERSION="green"
fi
# push a new version and map the route
cf cs p-rabbitmq standard p-rabbitmq
cf cs p-redis shared-vm p-redis
cf p "$CF_APP-$CURRENT_VERSION" -n "$CF_APP-$CURRENT_VERSION"
cf map-route "$CF_APP-$CURRENT_VERSION" $CF_APPS_DOMAIN -n $CF_APP
if [ ! -z "$DEPLOYED_VERSION" ]; then
  # Unmap the route and delete
  cf unmap-route "$CF_APP-$DEPLOYED_VERSION" $CF_APPS_DOMAIN -n $CF_APP
  # Scaling down
  cf scale "$CF_APP-$DEPLOYED_VERSION" -i 1
fi
