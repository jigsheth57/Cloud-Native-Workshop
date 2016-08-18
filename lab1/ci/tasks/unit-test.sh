#!/usr/bin/env bash
set -e

rabbitmq-server start -detached

cd lab-repo/lab1/contactDataService

mvn clean package -DskipTests=true -Djava.version=1.7

rabbitmqctl status

mvn test
