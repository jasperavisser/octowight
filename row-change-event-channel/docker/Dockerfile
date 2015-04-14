FROM oracle-java8

RUN useradd -ms /bin/bash app
USER app

ADD app /opt/app
WORKDIR /opt/app

ENV kafka.hostname kafka-broker
ENV kafka.topic.events row-change-events
ENV postgres.hostname postgres
ENV postgres.port 5432
ENV postgres.username postgres
ENV postgres.database postgres

ENTRYPOINT ["java", "-cp", "/opt/app/*", "nl.haploid.octowight.App"]
