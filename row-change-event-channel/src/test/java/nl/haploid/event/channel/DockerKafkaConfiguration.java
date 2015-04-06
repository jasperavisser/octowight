package nl.haploid.event.channel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Configuration
public class DockerKafkaConfiguration extends KafkaConfiguration {

    @Autowired
    private DockerService dockerService;

    @Override
    public String getHostname() {
        return dockerService.getHostname();
    }

    @Override
    public int getPort() {
        return 9092;
    }

    @PostConstruct
    public void startContainers() {
        // TODO: start zookeeper, then broker with link to zk
    }

    @PreDestroy
    public void stopContainers() {
        // TODO: stop containers
    }
}
