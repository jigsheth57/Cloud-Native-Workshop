#!/bin/bash
CF_APP=${1:-contactWebApp}
CF_APPS_DOMAIN=${2:-local.pcfdev.io}

mvn clean install package -D skipTests
if [ "$?" -ne "0" ]; then
  exit $?
fi

DEPLOYED_VERSION_CMD=$(CF_COLOR=false cf routes | grep $CF_APP | awk 'NR==1{ print $4; }' | cut -d"-" -f2)
DEPLOYED_VERSION="$DEPLOYED_VERSION_CMD"
CURRENT_VERSION="blue"
echo "Deployed Version: $DEPLOYED_VERSION"
if [ ! -z "$DEPLOYED_VERSION" -a "$DEPLOYED_VERSION" == "blue" ]; then
  CURRENT_VERSION="green"
  echo "New Version: $CURRENT_VERSION"
fi
# push a new version and map the route
cf cs p-redis shared-vm session-state
cf cs p-rabbitmq standard p-rabbitmq
cf p "$CF_APP-$CURRENT_VERSION" -n "$CF_APP-$CURRENT_VERSION"
cf map-route "$CF_APP-$CURRENT_VERSION" $CF_APPS_DOMAIN -n $CF_APP
if [ ! -z "$DEPLOYED_VERSION" ]; then
  # Unmap the route and delete
  cf unmap-route "$CF_APP-$DEPLOYED_VERSION" $CF_APPS_DOMAIN -n $CF_APP
  # Scaling down
  cf scale "$CF_APP-$DEPLOYED_VERSION" -i 1
fi
