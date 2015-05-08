package nl.haploid.octowight.registry

import nl.haploid.octowight.registry.configuration.{MongoConfiguration, TestConfiguration}
import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.support.AnnotationConfigContextLoader
import org.springframework.test.context.{ContextConfiguration, TestContextManager}

@RunWith(classOf[JUnitRunner])
@ContextConfiguration(classes = Array(classOf[TestConfiguration], classOf[MongoConfiguration]),
  loader = classOf[AnnotationConfigContextLoader])
abstract class AbstractIT extends FlatSpec with BeforeAndAfterEach with ShouldMatchers {
  protected var log = LoggerFactory.getLogger(getClass)

  @Autowired private val mongoConfiguration: MongoConfiguration = null

  override def beforeEach() = {
    new TestContextManager(this.getClass).prepareTestInstance(this)
    log.debug(s"Using mongo database: ${mongoConfiguration.getMongoDatabase}")
  }
}
