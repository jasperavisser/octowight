package nl.haploid.event.channel

import grizzled.slf4j.Logging
import org.scalatest._
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.support.AnnotationConfigContextLoader
import org.springframework.test.context.{ContextConfiguration, TestContextManager}

@ContextConfiguration(
  classes = Array(classOf[AppConfiguration], classOf[DockerConfiguration], classOf[ContainerizedPostgresConfiguration]),
  loader = classOf[AnnotationConfigContextLoader])
@ComponentScan
abstract class AbstractIT extends FlatSpec with ShouldMatchers with Logging {

  def setup = new TestContextManager(this.getClass).prepareTestInstance(this)
}

