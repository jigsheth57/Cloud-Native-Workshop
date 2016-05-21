#!/bin/bash
set -e
function jsonValue() {
  KEY=$1
  num=$2
  awk -F"[,:}]" '{for(i=1;i<=NF;i++){if($i~/'$KEY'\042/){print $(i+1)}}}' | tr -d '"' | sed -n ${num}p
}

# mvn clean install package -D skipTests=true
cf t
echo -n "Validate the space & org, you are currently logged in before continuing!"
read
cf cs p-mysql 512mb p-mysql
cf cs p-rabbitmq standard p-rabbitmq
cf p

# Generate User Specified Service Instance for this app, so other apps can easyily locate such as contactWebApp
#appdomain=`cf curl /v2/domains | jsonValue name 1 | sed -e 's/^[[:space:]]*//'`
#echo "App Domain: $appdomain"
#A_GUID=`cf curl /v2/apps?q=name:contactDataService | jsonValue guid 1 | sed -e 's/^[[:space:]]*//'`
#app_host=`cf curl /v2/apps/${A_GUID}/routes  | jsonValue host 1 | sed -e 's/^[[:space:]]*//'`
#echo "App Host: $app_host"
app_fqdn=`cf app contactDataService | awk '/urls: / {print $2}'`
csJSONStr={\"tag\":\"contact-service\",\"uri\":\"$app_fqdn\"}
echo \"$csJSONStr\"

cf cups contact-service -p \"$csJSONStr\"
if [ "$?" -ne "0" ]; then
  cf update-user-provided-service contact-service -p \"$csJSONStr\"
  if [ "$?" -ne "0" ]; then
    exit $?
  fi
fi
