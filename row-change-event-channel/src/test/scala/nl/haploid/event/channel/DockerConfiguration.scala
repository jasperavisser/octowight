package nl.haploid.event.channel

import com.github.dockerjava.api.DockerClient
import com.github.dockerjava.api.command.CreateContainerResponse
import com.github.dockerjava.core.DockerClientBuilder
import org.springframework.context.annotation.{Bean, Configuration}

@Configuration
class DockerConfiguration {

  @Bean
  def docker: DockerClient = DockerClientBuilder.getInstance.build

  @Bean
  def postgresContainer(docker: DockerClient): CreateContainerResponse = {
    val container = docker.createContainerCmd("postgres:9.2").exec
    docker.startContainerCmd(container.getId).exec
    container
  }
}
