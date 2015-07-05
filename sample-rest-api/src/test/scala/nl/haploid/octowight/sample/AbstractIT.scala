package nl.haploid.octowight.sample

import nl.haploid.octowight.sample.configuration.{PostgresConfiguration, TestConfiguration, WebMvcConfiguration}
import org.scalatest._
import org.scalatest.tags.Slow
import org.springframework.test.context.web.{AnnotationConfigWebContextLoader, WebAppConfiguration}
import org.springframework.test.context.{ContextConfiguration, TestContextManager}

@ContextConfiguration(classes = Array(classOf[TestConfiguration], classOf[PostgresConfiguration], classOf[WebMvcConfiguration]),
  loader = classOf[AnnotationConfigWebContextLoader])
@WebAppConfiguration
@Slow
abstract class AbstractIT extends FlatSpec with BeforeAndAfterEach with ShouldMatchers {

  override def beforeEach() = new TestContextManager(this.getClass).prepareTestInstance(this)
}
