package nl.haploid.event.channel

import com.github.dockerjava.core.{DockerClientBuilder, DockerClientConfig}
import grizzled.slf4j.Logging
import org.scalatest._
import org.springframework.test.context.support.AnnotationConfigContextLoader
import org.springframework.test.context.{ContextConfiguration, TestContextManager}

@ContextConfiguration(
  classes = Array(classOf[AppConfiguration], classOf[PostgresConfiguration]),
  loader = classOf[AnnotationConfigContextLoader])
abstract class AbstractIT extends FlatSpec with ShouldMatchers with Logging {

  // TODO: build fixture only once for all IT (maybe premature optimization)
  // TODO: make this work through boot2docker
  // TODO: move container configuration to separate class/file
  // TODO: maybe beans for the containers? that would make them singleton also
  def fixture =
    new {
      val config = DockerClientConfig.createDefaultConfigBuilder.build

      val docker = DockerClientBuilder.getInstance(config).build
      val container = docker.createContainerCmd("postgres:9.2").exec
      val containerId: String = container.getId

      docker.startContainerCmd(containerId).exec
      val ip = docker.inspectContainerCmd(containerId).exec.getNetworkSettings.getIpAddress
      warn("XXXXXXX %s".format(ip))
      Thread.sleep(3000)
      docker.stopContainerCmd(containerId).exec

      // @Bean DockerClient
      // @Bean PostgresContainer
      // PropertySource to inspect running PostgresContainer
      // How to ensure container is stopped after the tests? (and maybe even before)
    }

  val f = fixture

  new TestContextManager(this.getClass).prepareTestInstance(this)
}

