package nl.haploid.adapter

import javax.sql.DataSource

import org.scalatest.mock.EasyMockSugar
import org.scalatest.{FlatSpec, Matchers}
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.support.AnnotationConfigContextLoader

@ContextConfiguration(
  classes = Array(classOf[AppConfiguration]),
  loader = classOf[AnnotationConfigContextLoader])
class MockTest extends FlatSpec with EasyMockSugar with Matchers {

  "Something" should "just work" in {
    val mockDataSource = mock[DataSource]
    val service = new SomeService(mockDataSource)
    implicit val mocks = MockObjects(mockDataSource)
    expecting {
      mockDataSource.getConnection andReturn null
    }
    whenExecuting {
      service.getConnection should be(null)
    }
  }
}