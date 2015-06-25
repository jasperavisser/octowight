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

To run the sample apps:
```bash
bash bin/it/build-images.sh && bash bin/it/start-containers.sh
bash bin/sample/build-images.sh && bash bin/sample/start-containers.sh
```

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
* https://github.com/spotify/docker-maven-plugin

### Kafka
* http://www.quora.com/Which-one-is-better-for-durable-messaging-with-good-query-features-RabbitMQ-or-Kafka
* http://wurstmeister.github.io/kafka-docker/
* http://kafka.apache.org/documentation.html

### Maven
* http://stackoverflow.com/questions/24705877/cant-get-maven-to-recognize-java-1-8

## TODO

### Presentation
Explain:
	abstract idea
	tie-ins to other ideas
	moving parts
	assumptions
