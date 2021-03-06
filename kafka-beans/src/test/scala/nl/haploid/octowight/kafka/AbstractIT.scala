package nl.haploid.octowight.kafka

import nl.haploid.octowight.kafka.configuration.TestConfiguration
import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner
import org.scalatest.tags.Slow
import org.slf4j.LoggerFactory
import org.springframework.test.context.support.AnnotationConfigContextLoader
import org.springframework.test.context.{ContextConfiguration, TestContextManager}

@RunWith(classOf[JUnitRunner])
@ContextConfiguration(classes = Array(classOf[TestConfiguration]), loader = classOf[AnnotationConfigContextLoader])
@Slow
abstract class AbstractIT extends FlatSpec with BeforeAndAfterEach with ShouldMatchers {
  protected lazy val log = LoggerFactory.getLogger(getClass)

  override def beforeEach() = new TestContextManager(this.getClass).prepareTestInstance(this)
}
