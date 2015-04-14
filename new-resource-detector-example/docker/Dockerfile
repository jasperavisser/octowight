FROM oracle-java8

RUN useradd -ms /bin/bash app
USER app

ADD app /opt/app
WORKDIR /opt/app

ENV kafka.hostname kafka-broker
ENV kafka.consumer.timeout.ms 200
ENV kafka.topic.events row-change-events
ENV kafka.topic.resources.dirty dirty-resources
ENV postgres.hostname postgres
ENV postgres.port 5432
ENV postgres.username postgres
ENV postgres.database postgres
ENV redis.hostname redis
ENV redis.port 6379
ENV zookeeper.hostname zookeeper

ENTRYPOINT ["java", "-cp", "/opt/app/*", "nl.haploid.octowight.App"]
