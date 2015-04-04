package nl.haploid.event.channel

import com.github.dockerjava.api.DockerClient
import com.github.dockerjava.core.DockerClientBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.{Bean, Configuration}

@Configuration
class DockerConfiguration {

  @Value("${DOCKER_HOST}") val host: String = null

  @Bean
  def docker: DockerClient = DockerClientBuilder.getInstance.build
}
