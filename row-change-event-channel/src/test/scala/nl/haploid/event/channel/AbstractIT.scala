package nl.haploid.event.channel

import org.scalatest._
import org.springframework.test.context.support.AnnotationConfigContextLoader
import org.springframework.test.context.{ContextConfiguration, TestContextManager}

@ContextConfiguration(
  classes = Array(classOf[AppConfiguration], classOf[PostgresConfiguration]),
  loader = classOf[AnnotationConfigContextLoader])
abstract class AbstractIT extends FlatSpec with ShouldMatchers {

  new TestContextManager(this.getClass).prepareTestInstance(this)
}
