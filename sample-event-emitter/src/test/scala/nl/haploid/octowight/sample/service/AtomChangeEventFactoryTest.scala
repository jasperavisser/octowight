package nl.haploid.octowight.sample.service

import mockit.Tested
import nl.haploid.octowight.EasyMockInjection
import nl.haploid.octowight.sample.TestData
import org.junit.Assert.assertEquals
import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner
import org.scalatest.mock.EasyMockSugar

@RunWith(classOf[JUnitRunner])
class AtomChangeEventFactoryTest extends FlatSpec with EasyMockSugar
with ShouldMatchers with EasyMockInjection with BeforeAndAfterEach {

  @Tested private val eventFactory = new AtomChangeEventFactory

  "Atom change event factory" should "build event from DMO" in {
    val eventDmo = TestData.atomChangeEventDmo
    val event = eventFactory.fromAtomChangeEventDmo(eventDmo)
    assertEquals(eventDmo.getId, event.getId)
    assertEquals(eventDmo.getAtomId, event.getAtomId)
    assertEquals(eventDmo.getAtomType, event.getAtomType)
  }

  "Atom change event factory" should "build event from multiple DMOs" in {
    val eventDmo1 = TestData.atomChangeEventDmo
    val eventDmo2 = TestData.atomChangeEventDmo
    val eventDmos = List(eventDmo1, eventDmo2)
    val actualEvents = eventFactory.fromAtomChangeEventDmos(eventDmos)
    actualEvents should have size 2
  }
}
