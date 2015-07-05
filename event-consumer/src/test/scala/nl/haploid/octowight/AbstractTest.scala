package nl.haploid.octowight

import org.scalatest._
import org.scalatest.mock.EasyMockSugar

abstract class AbstractTest extends FlatSpec with EasyMockSugar with ShouldMatchers with EasyMockInjection with BeforeAndAfterEach {

  override def beforeEach() = injectMocks()
}
