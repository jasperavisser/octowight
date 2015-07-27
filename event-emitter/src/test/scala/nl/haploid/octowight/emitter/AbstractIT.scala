package nl.haploid.octowight.emitter

import nl.haploid.octowight.emitter.configuration.{TestConfiguration, KafkaConfiguration}
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.tags.Slow
import org.scalatest.{BeforeAndAfterEach, FlatSpec, ShouldMatchers}
import org.springframework.test.context.support.AnnotationConfigContextLoader
import org.springframework.test.context.{ContextConfiguration, TestContextManager}

@RunWith(classOf[JUnitRunner])
@ContextConfiguration(classes = Array(classOf[TestConfiguration], classOf[KafkaConfiguration]),
  loader = classOf[AnnotationConfigContextLoader])
@Slow
abstract class AbstractIT extends FlatSpec with BeforeAndAfterEach with ShouldMatchers {

  override def beforeEach() = new TestContextManager(this.getClass).prepareTestInstance(this)
}
