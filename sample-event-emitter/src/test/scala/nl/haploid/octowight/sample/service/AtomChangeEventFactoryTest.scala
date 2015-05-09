package nl.haploid.octowight.sample.service

import nl.haploid.octowight.Tested
import nl.haploid.octowight.sample.{AbstractTest, TestData}

class AtomChangeEventFactoryTest extends AbstractTest {
  @Tested private val eventFactory: AtomChangeEventFactory = null

  "Atom change event factory" should "build event from DMO" in {
    val eventDmo = TestData.atomChangeEventDmo
    val event = eventFactory.fromAtomChangeEventDmo(eventDmo)
    event.getId should be(eventDmo.getId)
    event.getAtomId should be(eventDmo.getAtomId)
    event.getAtomType should be(eventDmo.getAtomType)
  }

  "Atom change event factory" should "build event from multiple DMOs" in {
    val eventDmo1 = TestData.atomChangeEventDmo
    val eventDmo2 = TestData.atomChangeEventDmo
    val eventDmos = List(eventDmo1, eventDmo2)
    val actualEvents = eventFactory.fromAtomChangeEventDmos(eventDmos)
    actualEvents should have size 2
  }
}
