package nl.haploid.event.channel;

import com.github.dockerjava.api.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Arrays;
import java.util.List;

@Configuration
public class DockerKafkaConfiguration extends KafkaConfiguration {

    private static final String CONTAINER_NAME = "kafka_it";
    private static final String IMAGE_NAME = "wurstmeister/kafka:0.8.2.0";
    private static final int PORT = 9092;

    @Autowired
    private DockerService dockerService;

    @Override
    public String getHostname() {
        return dockerService.getHostname();
    }

    @Override
    public int getPort() {
        return PORT;
    }

    @PostConstruct
    public void startContainer() throws InterruptedException {
        final Container container = dockerService.getContainerByName(CONTAINER_NAME);
        if (container == null || !dockerService.isRunning(container)) {
            final Ports ports = new Ports(ExposedPort.tcp(getPort()), Ports.Binding(getPort()));
            final Binds binds = new Binds(Bind.parse("/var/run/docker.sock:/var/run/docker.sock"));
            final Links links = new Links(Link.parse("zookeeper_it:zk"));
            final List<String> envs = Arrays.asList(String.format("KAFKA_ADVERTISED_HOST_NAME=%s", getHostname()));
            dockerService.startContainer(IMAGE_NAME, CONTAINER_NAME, ports, binds, links, envs);
        }
    }

    @PreDestroy
    public void stopContainers() {
        // TODO: stop containers
    }
}
