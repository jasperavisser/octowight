package nl.haploid.event.channel;

import com.github.dockerjava.api.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;

@Configuration
@EnableJpaRepositories(basePackages = {"nl.haploid.event.channel.repository"})
@EnableTransactionManagement
public class DockerPostgresConfiguration extends PostgresConfiguration {

    private static final int PORT = 5432;
    private static final String USERNAME = "postgres";
    private static final String DATABASE = "postgres";
    private static final String IMAGE_NAME = "postgres:9.2";
    private static final String CONTAINER_NAME = "postgres_it";
    private static final int POSTGRES_STARTUP_DELAY_MS = 5000;

    @Autowired
    private DockerService dockerService;

    @Override
    protected String getHostname() {
        return dockerService.getHostname();
    }

    @Override
    protected int getPort() {
        return PORT;
    }

    @Override
    protected String getUsername() {
        return USERNAME;
    }

    @Override
    protected String getDatabase() {
        return DATABASE;
    }

    @PostConstruct
    public void startContainer() throws InterruptedException {
        final Container container = dockerService.getContainerByName(CONTAINER_NAME);
        if (container == null || !dockerService.isRunning(container)) {
            final Ports ports = new Ports(ExposedPort.tcp(getPort()), Ports.Binding(getPort()));
            final Binds binds = new Binds();
            final Links links = new Links();
            dockerService.startContainer(IMAGE_NAME, CONTAINER_NAME, ports, binds, links, new ArrayList<String>());
            Thread.sleep(POSTGRES_STARTUP_DELAY_MS);
        }
    }

    @PreDestroy
    public void stopContainer() {
        final Container container = dockerService.getContainerByName(CONTAINER_NAME);
        if (container != null) {
            // TODO: dockerService.stopContainer(container);
        }
    }
}
