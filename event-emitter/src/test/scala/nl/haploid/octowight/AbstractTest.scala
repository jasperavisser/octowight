package nl.haploid.octowight

import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner
import org.scalatest.mock.EasyMockSugar

@RunWith(classOf[JUnitRunner])
class AbstractTest extends FlatSpec with EasyMockSugar with ShouldMatchers with EasyMockInjection with BeforeAndAfterEach {

   override def beforeEach() = injectMocks()
 }
