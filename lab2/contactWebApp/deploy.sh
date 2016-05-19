#!/bin/bash

function jsonValue() {
  KEY=$1
  num=$2
  awk -F"[,:}]" '{for(i=1;i<=NF;i++){if($i~/'$KEY'\042/){print $(i+1)}}}' | tr -d '"' | sed -n ${num}p
}

  # mvn clean install package -D skipTests=true
  cf t
  echo -n "Validate the space & org, you are currently logged in before continuing!"
  read
  cf cs p-rabbitmq standard p-rabbitmq
  cf p
