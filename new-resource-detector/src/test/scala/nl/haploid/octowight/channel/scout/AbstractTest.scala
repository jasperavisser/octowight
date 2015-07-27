package nl.haploid.octowight.channel.scout

import nl.haploid.octowight.EasyMockInjection
import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner
import org.scalatest.mock.EasyMockSugar

@RunWith(classOf[JUnitRunner])
abstract class AbstractTest extends FlatSpec with EasyMockSugar with ShouldMatchers with EasyMockInjection with BeforeAndAfterEach {

  override def beforeEach() = injectMocks()
}
