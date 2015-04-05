package nl.haploid.event.channel;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DockerClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DockerConfiguration {

    @Value("${DOCKER_HOST}")
    private String host;

    @Bean
    public DockerClient docker() {
        return DockerClientBuilder.getInstance().build();
    }
}
