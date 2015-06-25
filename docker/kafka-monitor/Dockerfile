FROM oracle-java8

RUN yum install -y tar

ENV KAFKA_VERSION 0.8.2.1
RUN curl -LO "http://mirror.nl.webzilla.com/apache/kafka/${KAFKA_VERSION}/kafka_2.11-${KAFKA_VERSION}.tgz" \
	&& tar xzf "kafka_2.11-${KAFKA_VERSION}.tgz" \
	&& mv "kafka_2.11-${KAFKA_VERSION}" "kafka" \
	&& rm -f "kafka_2.11-${KAFKA_VERSION}.tgz"
WORKDIR /kafka

ENTRYPOINT ["bash", "bin/kafka-console-consumer.sh", "--from-beginning", "--zookeeper", "zk:2181", "--blacklist", "nothing"]
