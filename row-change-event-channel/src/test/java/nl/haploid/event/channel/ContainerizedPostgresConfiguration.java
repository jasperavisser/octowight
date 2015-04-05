package nl.haploid.event.channel;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.*;
import com.jolbox.bonecp.BoneCPDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Configuration
public class ContainerizedPostgresConfiguration extends PostgresConfiguration {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Value("${DOCKER_HOST}")
    private String dockerHost;

    @Autowired
    private DockerClient docker;

    private int port = 5432;
    private String username = "postgres";
    private String database = "postgres";
    private String imageName = "postgres:9.2";
    private String containerName = "postgres_it";

    @Override
    public String getHostname() {
        return dockerHost.replaceAll("^.*?(\\d+[.]\\d+[.]\\d+[.]\\d+).*?$", "$1");
    }

    public CreateContainerResponse startContainer() throws InterruptedException, IOException {
        final ExposedPort exposedPort = ExposedPort.tcp(port);
        final CreateContainerResponse container = docker.createContainerCmd(imageName)
                .withName(containerName)
                .withExposedPorts(exposedPort)
                .exec();
        final Ports portBindings = new Ports(exposedPort, Ports.Binding(port));
        final String tempDir = Files.createTempDirectory("postgres").toFile().getAbsolutePath();
        docker.startContainerCmd(container.getId())
                .withBinds(new Bind(tempDir, new Volume("/var/lib/postgresql/data")))
                .withPortBindings(portBindings)
                .exec();
        docker.waitContainerCmd(container.getId());
        Thread.sleep(5000);
        return container;
    }

    public void stopContainer() {
        final List<Container> containers = docker
                .listContainersCmd()
                .withShowAll(true)
                .exec();
        for (final Container container : containers) {
            for (final String name : container.getNames()) {
                if (name.equals(String.format("/%s", containerName))) {
                    if (docker.inspectContainerCmd(container.getId()).exec().getState().isRunning()) {
                        docker.stopContainerCmd(container.getId()).exec();
                    }
                    docker.removeContainerCmd(container.getId()).exec();
                }
            }
        }
    }

    @Bean
    @Override
    public DataSource dataSource() {

        stopContainer();
        try {
            startContainer();
        } catch (Exception e) {
            throw new RuntimeException("Could not start container!", e);
        }

        final BoneCPDataSource dataSource = new BoneCPDataSource();
        dataSource.setDriverClass("org.postgresql.Driver");
        final String jdbcUrl = String.format("jdbc:postgresql://%s:%d/%s", getHostname(), port, database);
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(username);
        log.debug(String.format("Will connect to %s as %s", jdbcUrl, username));
        return dataSource;
    }
}
