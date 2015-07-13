# octowight [![Build Status](https://travis-ci.org/jasperavisser/octowight.svg?branch=master)](https://travis-ci.org/jasperavisser/octowight) [![Codacy Badge](https://www.codacy.com/project/badge/9541b85030e24d1b9b170e19cec3b0d5)](https://www.codacy.com/app/jasper-a-visser/octowight)
Octowight is a mechanism for the event-driven population of a REST API.

## Modules

### Skeleton apps
- **dirty-resource-detector** = skeleton app that detects which REST resources have changed
- **event-emitter** = skeleton app for emitting changes in the original data
- **new-resource-detector** = skeleton app that detects new REST resources
- **resource-builder** = skeleton app for building REST resources

These skeleton apps are intended to be extended to read from your specific data source, and build REST resources of your specific type.

### Libraries
- **atom-change-event** = data transfer objects that represent changes in the original data
- **event-consumer** = Spring beans for consuming changes in the original data
- **kafka-beans** = Spring beans for interfacing with Kafka
- **resource-registry** = interface with a resource registry
- **spring-scala-mock** = tools for mocking Spring beans in scalatest

## Requirements

### Linux
Add this line to */etc/init/docker.conf*

```bash
DOCKER_OPTS='-H tcp://0.0.0.0:4243 -H unix:///var/run/docker.sock'
```
### Fedora
Add this line to  */etc/sysconfig/docker*

```bash
OPTIONS='-H tcp://0.0.0.0:4243 -H unix:///var/run/docker.sock'
```
### Mac OS
Install *greadlink*

```bash
brew install coreutils
sudo ln -s /usr/local/bin/greadlink /usr/local/bin/readlink
```

Install *boot2docker* from http://boot2docker.io/

## Installation

### Bootstrap
Copy the file *bootstrap.env.template* to *bootstrap.env* and edit it to match your configuration.

### Build 
```bash
gradle assemble
```

### Start infrastructure containers
```bash
bash bin/build-images.sh
docker-compose -f infrastructure.yml up -d
```

### Run integration tests
```bash
gradle IT
```

### Run integration tests in IDE
Run \*IT.scala with environment variable *INFRASTRUCTURE_HOST = your docker host* (usually localhost).

## Design principles
TODO: Design principles
TODO: Adopt names from EDA

### Assumptions
* Each resource has a single atom that represents its main component
* Each atom represents no more than a single resource of any given type
* Each atom can represent resources of multiple types
* Order of atom change events does not matter
* It suffices to send third parties a stream of dirty resources
* TODO: more assumptions

## References

### Design principles
* https://engineering.linkedin.com/distributed-systems/log-what-every-software-engineer-should-know-about-real-time-datas-unifying
* http://en.wikipedia.org/wiki/Event-driven_architecture
* https://www.voxxed.com/blog/2015/04/coupling-versus-autonomy-in-microservices/

### Docker
* https://docs.docker.com/reference/builder/
* http://boot2docker.io/
* http://www.virtuallyghetto.com/2014/07/quick-tip-how-to-enable-docker-remote-api.html
* https://docs.docker.com/compose/yml/

### Kafka
* http://www.quora.com/Which-one-is-better-for-durable-messaging-with-good-query-features-RabbitMQ-or-Kafka
* http://wurstmeister.github.io/kafka-docker/
* http://kafka.apache.org/documentation.html

### Gradle
* https://github.com/spring-gradle-plugins/dependency-management-plugin
* https://github.com/Transmode/gradle-docker

### Spring
* https://spring.io/guides/gs/spring-boot-docker/
