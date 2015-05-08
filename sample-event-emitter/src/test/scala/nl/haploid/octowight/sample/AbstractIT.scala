package nl.haploid.octowight.sample

import nl.haploid.octowight.configuration.KafkaConfiguration
import nl.haploid.octowight.sample.configuration.{PostgresConfiguration, TestConfiguration}
import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner
import org.springframework.test.context.support.AnnotationConfigContextLoader
import org.springframework.test.context.{ContextConfiguration, TestContextManager}

@RunWith(classOf[JUnitRunner])
@ContextConfiguration(classes = Array(classOf[TestConfiguration], classOf[KafkaConfiguration], classOf[PostgresConfiguration]), loader = classOf[AnnotationConfigContextLoader])
abstract class AbstractIT extends FlatSpec with BeforeAndAfterEach with ShouldMatchers {

  override def beforeEach() = new TestContextManager(this.getClass).prepareTestInstance(this)
}
