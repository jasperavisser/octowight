package nl.haploid.event.channel

import javax.sql.DataSource

import grizzled.slf4j.Logging
import org.scalatest._
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.ComponentScan
import org.springframework.core.io.ClassPathResource
import org.springframework.jdbc.datasource.init.ScriptUtils
import org.springframework.test.context.support.AnnotationConfigContextLoader
import org.springframework.test.context.{ContextConfiguration, TestContextManager}

@ContextConfiguration(
  classes = Array(classOf[AppConfiguration], classOf[DockerConfiguration], classOf[ContainerizedPostgresConfiguration]),
  loader = classOf[AnnotationConfigContextLoader])
@ComponentScan
abstract class AbstractIT extends FlatSpec with ShouldMatchers with Logging {

  @Autowired val dataSource: DataSource = null

  def initializeDatabase = ScriptUtils.executeSqlScript(dataSource.getConnection, new ClassPathResource("/initialize-database.sql", getClass))

  def setup = {
    new TestContextManager(this.getClass).prepareTestInstance(this)
    initializeDatabase
  }
}

