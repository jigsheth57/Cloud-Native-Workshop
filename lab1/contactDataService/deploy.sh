#!/bin/bash

function jsonValue() {
  KEY=$1
  num=$2
  awk -F"[,:}]" '{for(i=1;i<=NF;i++){if($i~/'$KEY'\042/){print $(i+1)}}}' | tr -d '"' | sed -n ${num}p
}

# mvn clean package -DskipTests
cf t
echo -n "Validate the space & org, you are currently logged in before continuing!"
read
cf cs p-mysql 100mb p-mysql
cf cs cloudamqp lemur p-rabbitmq
cf p

# Generate User Specified Service Instance for this app, so other apps can easyily locate such as contactWebApp
appdomain=`cf curl /v2/domains | jsonValue name 1 | sed -e 's/^[[:space:]]*//'`
A_GUID=`cf curl /v2/apps?q=name:contactDataService | jsonValue guid 1 | sed -e 's/^[[:space:]]*//'`
app_host=`cf curl /v2/apps/${A_GUID}/routes  | jsonValue host 1 | sed -e 's/^[[:space:]]*//'`

cf cups contact-service -p \"{\"tag\":\"contact-service\",\"uri\":\"http://$app_host.$appdomain\"}\"
