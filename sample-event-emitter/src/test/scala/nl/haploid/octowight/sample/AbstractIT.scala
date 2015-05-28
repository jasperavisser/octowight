package nl.haploid.octowight.sample

import javax.sql.DataSource

import nl.haploid.octowight.configuration.KafkaConfiguration
import nl.haploid.octowight.sample.configuration.{PostgresConfiguration, TestConfiguration}
import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.support.AnnotationConfigContextLoader
import org.springframework.test.context.{ContextConfiguration, TestContextManager}
import slick.driver.PostgresDriver.api._

@RunWith(classOf[JUnitRunner])
@ContextConfiguration(classes = Array(classOf[TestConfiguration], classOf[KafkaConfiguration], classOf[PostgresConfiguration]), loader = classOf[AnnotationConfigContextLoader])
abstract class AbstractIT extends FlatSpec with BeforeAndAfterEach with BeforeAndAfterAll with ShouldMatchers {
  @Autowired private[this] val dataSource: DataSource = null

  // TODO: with transaction, rollback
  // TODO: get rid of entity manager, JPA, hibernate
  lazy val database = Database.forDataSource(dataSource)

  override def beforeEach() = new TestContextManager(this.getClass).prepareTestInstance(this)

  override def afterAll() = database.close()
}
