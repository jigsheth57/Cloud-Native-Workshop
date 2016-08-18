#!/usr/bin/env bash
set -e
version=`cat version/number`

rabbitmq-server start -detached

cd lab-repo/lab1/contactDataService

mvn clean versions:set -DnewVersion=$version
mvn package -DskipTests=true -Djava.version=1.7

rabbitmqctl status

mvn test

pwd
ls -laF target/*.jar
mv target/*.jar ../../../build-artifact
ls -laF ../../../build-artifact
