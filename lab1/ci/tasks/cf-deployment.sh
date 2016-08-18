#!/usr/bin/env bash
set -x

# login to PWS
echo "Login to PWS ....."

target="cf login -a $API_ENDPOINT -u $USERNAME -p $PASSWORD -o $ORG -s $SPACE --skip-ssl-validation"
#echo $target
eval $target

DEPLOYED_VERSION_CMD=$(CF_COLOR=false cf a | grep $CF_APP_NAME- | cut -d" " -f1| cut -d"-" -f2)
DEPLOYED_VERSION="$DEPLOYED_VERSION_CMD"
echo "Deployed Version: $DEPLOYED_VERSION"
CURRENT_VERSION="blue"
if [ ! -z "$DEPLOYED_VERSION" -a "$DEPLOYED_VERSION" == "blue" ]; then
  CURRENT_VERSION="green"
fi
# push a new version and map the route
cf cs cleardb spark p-mysql
cf cs cloudamqp lemur p-rabbitmq
cf p "$CF_APP_NAME-$CURRENT_VERSION" -p lab-release/contactDataService-*.jar -n "$CF_APP_NAME-$CURRENT_VERSION" -f lab-repo/lab1/ci/tasks/manifest.yml
cf map-route "$CF_APP_NAME-$CURRENT_VERSION" $CF_APPS_DOMAIN -n $CF_APP_NAME
if [ ! -z "$DEPLOYED_VERSION" ]; then
  # Unmap the route and delete
  cf unmap-route "$CF_APP_NAME-$DEPLOYED_VERSION" $CF_APPS_DOMAIN -n $CF_APP_NAME
  # Scaling down
  cf scale "$CF_APP_NAME-$DEPLOYED_VERSION" -i 1
fi
