# Running the labs locally

You will need to [install Docker](https://docs.docker.com/engine/installation/)

Once Docker is installed, start up the Docker CLI

. Retrieve Docker Machine IP
----
$ docker-machine ip
192.168.99.101

$ cd $WORKSHOP_HOME

$ docker-compose up
----

## Test Application

Point your browser to http://$DOCKER-MACHINE-IP:8090/  (hint. take a look at the labs doc for details on testing.)
