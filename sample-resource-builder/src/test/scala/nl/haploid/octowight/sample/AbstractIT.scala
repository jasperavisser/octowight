package nl.haploid.octowight.sample

import nl.haploid.octowight.sample.configuration.{TestConfiguration, KafkaConfiguration, PostgresConfiguration}
import org.scalatest._
import org.scalatest.tags.Slow
import org.springframework.test.context.support.AnnotationConfigContextLoader
import org.springframework.test.context.{ContextConfiguration, TestContextManager}

@ContextConfiguration(classes = Array(classOf[TestConfiguration], classOf[PostgresConfiguration], classOf[KafkaConfiguration]),
  loader = classOf[AnnotationConfigContextLoader])
@Slow
abstract class AbstractIT extends FlatSpec with BeforeAndAfterEach with ShouldMatchers {

  override def beforeEach() = new TestContextManager(this.getClass).prepareTestInstance(this)
}
