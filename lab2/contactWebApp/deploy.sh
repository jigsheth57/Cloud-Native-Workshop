#!/bin/bash

function jsonValue() {
  KEY=$1
  num=$2
  awk -F"[,:}]" '{for(i=1;i<=NF;i++){if($i~/'$KEY'\042/){print $(i+1)}}}' | tr -d '"' | sed -n ${num}p
}

if [ -n "$1" ]
then
  # mvn clean package -DskipTests
  cf t
  echo -n "Validate the space & org, you are currently logged in before continuing!"
  read
  cf cs cloudamqp lemur p-rabbitmq
  csJSONStr={\"tag\":\"contact-service\",\"uri\":\"$1\"}
  echo $csJSONStr
  cf cups contact-service -p ${csJSONStr}
  cf p 
else
  echo "Usage: deploy <contactDataService URI>"
fi
