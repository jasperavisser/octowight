package nl.haploid.event.channel;

import com.github.dockerjava.api.model.Bind;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.Ports;
import com.github.dockerjava.api.model.Volume;
import com.jolbox.bonecp.BoneCPDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@ComponentScan // TODO: okay, it's a little annoying to do a component scan here
public class ContainerizedPostgresConfiguration extends PostgresConfiguration {

    private static final int PORT = 5432;
    private static final String USERNAME = "postgres";
    private static final String DATABASE = "postgres";
    private static final String IMAGE_NAME = "postgres:9.2";
    private static final String CONTAINER_NAME = "postgres_it";

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Value("${DOCKER_HOST}")
    private String dockerHost;

    @Autowired
    private DockerService dockerService;

    @Override
    public String getHostname() {
        return dockerHost.replaceAll("^.*?(\\d+[.]\\d+[.]\\d+[.]\\d+).*?$", "$1");
    }

    private void waitForPostgresToStart() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    @Override
    public DataSource dataSource() {

        dockerService.stopContainer(CONTAINER_NAME);
        final Ports ports = new Ports(ExposedPort.tcp(PORT), Ports.Binding(PORT));
        final Bind bind = new Bind(dockerService.createTempDirectory(), new Volume("/var/lib/postgresql/data"));
        dockerService.startContainer(IMAGE_NAME, CONTAINER_NAME, ports, bind);
        waitForPostgresToStart();

        final BoneCPDataSource dataSource = new BoneCPDataSource();
        dataSource.setDriverClass("org.postgresql.Driver");
        final String jdbcUrl = String.format("jdbc:postgresql://%s:%d/%s", getHostname(), PORT, DATABASE);
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(USERNAME);
        log.debug(String.format("Will connect to %s as %s", jdbcUrl, USERNAME));
        return dataSource;
    }
}
