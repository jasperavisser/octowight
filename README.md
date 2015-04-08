# octowight
Just playing around with Scala, Spring, Docker

## Required

FOR greadlink:
brew install coreutils

Add this line to /etc/init/docker.conf
DOCKER_OPTS='-H tcp://0.0.0.0:4243 -H unix:///var/run/docker.sock'
http://www.virtuallyghetto.com/2014/07/quick-tip-how-to-enable-docker-remote-api.html

## TODO
TODO: add all required docker images (java7) somewhere
TODO: docker pull in bootstrap (plus figure out what to do with bootstrap)
TODO: script to run test consumer

### Choose how to run IT
* Containers for
	* PostgreSQL
	* Zookeeper
	* Kafka broker
	* Redis
	* Event source
	* Event consumer (new resources)
	* Event consumer (mark as dirty)
	* IT suite (separate maven module, test data, output as junit.xml)
* fig-it.yml

#### What about ITs inside the project?
* Run with failsafe plugin?
	* Start up requisite servers
	* https://docs.docker.com/reference/api/remote_api_client_libraries/
	* Set environment variables
	* Docker spring configuration annotation provider thingy (to docker inspect and connect to database)
	* http://scottfrederick.cfapps.io/blog/2012/05/22/Custom-PropertySource-in-Spring-3.1---Part-1
* Run in container?
	* Add module directory to a container that can run mvn
	* Build container
	* Run container with fig

### Choose & configure image for
* PostgreSQL (layer on top to initialize database for IT)
	* https://registry.hub.docker.com/_/postgres/
	* docker run -v "${MY_POSTGRES_DATA}":/var/lib/postgresql/data -d postgres
* Zookeeper
* Kafka broker
* Redis

### Event source
* (should be a library, so user can swap in another database?)
* Merge 2 projects (hibernate, scalatest)
* Make IT work with HSQLDB
* Add KafkaConfiguration
* Write to kafka (mocked in unit/IT)

### Event consumer (new resources)
* (library, so user can define resource detectors)
* Consume kafka topic
* Detect new resources
* Publish to redis & kafka

### Event consumer (mark as dirty)
* (library, so user can define resource detectors)
* Consume kafka topic
* Detect new resources
* Publish to redis & kafka
