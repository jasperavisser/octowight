package nl.haploid.octowight

import nl.haploid.octowight.configuration.KafkaConfiguration
import nl.haploid.octowight.configuration.TestConfiguration
import org.scalatest.{ShouldMatchers, BeforeAndAfterEach, FlatSpec}
import org.springframework.test.context.{TestContextManager, ContextConfiguration}
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests
import org.springframework.test.context.support.AnnotationConfigContextLoader

@ContextConfiguration(classes = Array(classOf[TestConfiguration], classOf[KafkaConfiguration]),
  loader = classOf[AnnotationConfigContextLoader])
abstract class AbstractIT extends FlatSpec with BeforeAndAfterEach with ShouldMatchers {

  override def beforeEach() = new TestContextManager(this.getClass).prepareTestInstance(this)
}
