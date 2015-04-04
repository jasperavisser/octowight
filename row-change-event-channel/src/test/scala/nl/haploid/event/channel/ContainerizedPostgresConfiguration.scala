package nl.haploid.event.channel

import java.nio.file.Files
import javax.sql.DataSource

import com.github.dockerjava.api.DockerClient
import com.github.dockerjava.api.model._
import com.jolbox.bonecp.BoneCPDataSource
import grizzled.slf4j.Logging
import org.springframework.beans.factory.annotation.{Autowired, Value}
import org.springframework.context.annotation.{Bean, Configuration}

import scala.collection.JavaConverters._

@Configuration
class ContainerizedPostgresConfiguration extends PostgresConfiguration with Logging {

  val IMAGE_NAME = "haploid/postgres:latest"
  val CONTAINER_NAME: String = "postgres_it"

  @Value("${DOCKER_HOST}") val dockerHost: String = null

  @Autowired val docker: DockerClient = null

  override val port: Int = 5432
  override val username = "postgres"
  override val database = "postgres"

  override def hostname = dockerHost.replaceAll("^.*?(\\d+[.]\\d+[.]\\d+[.]\\d+).*?$", "$1")

  def startContainer = {
    val exposedPort = ExposedPort.tcp(port)
    val container = docker.createContainerCmd(IMAGE_NAME)
      .withName(CONTAINER_NAME)
      .withExposedPorts(exposedPort)
      .exec
    val portBindings = new Ports(exposedPort, Ports.Binding(port))
    val tempDir = Files.createTempDirectory("postgres").toFile.getAbsolutePath
    docker.startContainerCmd(container.getId)
      .withBinds(new Bind(tempDir, new Volume("/var/lib/postgresql/data")))
      .withPortBindings(portBindings)
      .exec
    docker.waitContainerCmd(container.getId)
    Thread.sleep(5000)
    container
  }

  def stopContainer = {
    val containers = docker
      .listContainersCmd
      .withShowAll(true)
      .exec
      .asScala
      .filter(c => c.getNames.contains("/%s".format(CONTAINER_NAME)))
    containers
      .filter(c => docker.inspectContainerCmd(c.getId).exec.getState.isRunning)
      .foreach(c => docker.stopContainerCmd(c.getId).exec)
    containers.foreach(c => docker.removeContainerCmd(c.getId).exec)
    containers
  }

  @Bean
  override def dataSource: DataSource = {

    stopContainer
    startContainer

    val dataSource = new BoneCPDataSource
    dataSource.setDriverClass("org.postgresql.Driver")
    val jdbcUrl = "jdbc:postgresql://%s:%d/%s".format(hostname, port, database)
    dataSource.setJdbcUrl(jdbcUrl)
    dataSource.setUsername(username)
    dataSource.setPassword(username)
    debug("Will connect to %s as %s".format(jdbcUrl, username))
    dataSource
  }
}
