package nl.haploid.octowight.sample

import nl.haploid.octowight.configuration.KafkaConfiguration
import nl.haploid.octowight.sample.configuration.PostgresConfiguration
import nl.haploid.octowight.sample.configuration.TestConfiguration
import nl.haploid.octowight.sample.repository.AtomChangeEventDmoRepository
import org.junit.Before
import org.scalatest._
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.{TestContextManager, ContextConfiguration}
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests
import org.springframework.test.context.support.AnnotationConfigContextLoader

@ContextConfiguration(classes = Array(classOf[TestConfiguration], classOf[KafkaConfiguration], classOf[PostgresConfiguration]), loader = classOf[AnnotationConfigContextLoader])
abstract class AbstractIT extends FlatSpec with BeforeAndAfterEach with ShouldMatchers {

  override def beforeEach() = new TestContextManager(this.getClass).prepareTestInstance(this)
}
