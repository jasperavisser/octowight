package nl.haploid.octowight.sample.service

import mockit.Tested
import nl.haploid.octowight.sample.{AbstractTest, TestData}
import org.junit.Assert.assertEquals

class AtomChangeEventFactoryTest extends AbstractTest {
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
