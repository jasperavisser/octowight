FROM oracle-java8

RUN useradd -ms /bin/bash app
USER app

ADD app /opt/app
WORKDIR /opt/app

ENV OCTOWIGHT_KAFKA_HOSTNAME kafka-broker
ENV OCTOWIGHT_KAFKA_PORT 9092
ENV OCTOWIGHT_KAFKA_TOPIC_EVENTS row-change-events
ENV OCTOWIGHT_POSTGRES_HOSTNAME postgres-data-repository
ENV OCTOWIGHT_POSTGRES_PORT 5432
ENV OCTOWIGHT_POSTGRES_USERNAME postgres
ENV OCTOWIGHT_POSTGRES_DATABASE postgres

ENTRYPOINT ["java", "-cp", "/opt/app/*", "nl.haploid.octowight.sample.App"]
