FROM oracle-java8

RUN useradd -ms /bin/bash app
USER app

ADD app /opt/app
WORKDIR /opt/app

ENV postgres.hostname db
ENV postgres.port 5432
ENV postgres.username pgsql
ENV postgres.database portavita

ENTRYPOINT ["java", "-cp", "/opt/app/*", "nl.haploid.octowight.App"]
