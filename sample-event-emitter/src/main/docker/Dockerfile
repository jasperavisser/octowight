FROM oracle-java8

RUN useradd -ms /bin/bash app
USER app

ADD *.jar /opt/app/
WORKDIR /opt/app/

ENV OCTOWIGHT_KAFKA_HOSTNAME=kafka-broker \
	OCTOWIGHT_KAFKA_PORT=9092 \
	OCTOWIGHT_KAFKA_TOPIC_EVENTS=row-change-events \
	OCTOWIGHT_POSTGRES_HOSTNAME=postgres-data-repository \
	OCTOWIGHT_POSTGRES_PORT=5432 \
	OCTOWIGHT_POSTGRES_USERNAME=postgres \
	OCTOWIGHT_POSTGRES_DATABASE=postgres

ENTRYPOINT ["java", "-jar", "*.jar"]
