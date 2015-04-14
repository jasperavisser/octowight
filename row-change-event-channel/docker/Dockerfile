FROM java7

RUN useradd -ms /bin/bash app
USER app

ENV postgres.hostname db
ENV postgres.port 5432
ENV postgres.username pgsql
ENV postgres.database portavita

ENTRYPOINT ["java", "-cp", "/app/*", "nl.haploid.octowight.App"]
