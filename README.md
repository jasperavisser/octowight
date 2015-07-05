# octowight [![Build Status](https://travis-ci.org/jasperavisser/octowight.svg?branch=master)](https://travis-ci.org/jasperavisser/octowight) [![Codacy Badge](https://www.codacy.com/project/badge/9541b85030e24d1b9b170e19cec3b0d5)](https://www.codacy.com/app/jasper-a-visser/octowight)
TODO

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
TODO: How to install, run IT, run apps

To bootstrap the project, copy the file *bootstrap.env.template* to *bootstrap.env* and edit it to match your configuration.

To build the project:
```bash
bash bin/infrastructure/build-images.sh
docker-compose -f infrastructure.yml up -d
gradle assemble
```

To run the sample apps:
```bash
bash bin/infrastructure/build-images.sh
bash bin/sample/build-images.sh
docker-compose up -d
```

To run the integration tests in an IDE:
```bash
bash bin/infrastructure/build-images.sh
docker-compose -f infrastructure.yml up -d
```
then simply start your IT with an environment variable INFRASTRUCTURE_HOST that points to your docker host.

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

## TODO

### Presentation
Explain:
	abstract idea
	tie-ins to other ideas
	moving parts
	assumptions
