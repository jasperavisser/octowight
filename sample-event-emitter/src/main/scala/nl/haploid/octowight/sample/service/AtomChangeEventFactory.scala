package nl.haploid.octowight.sample.service

import nl.haploid.octowight.AtomChangeEvent
import nl.haploid.octowight.sample.repository.AtomChangeEventDmo
import org.springframework.stereotype.Component

@Component
class AtomChangeEventFactory {

  def fromAtomChangeEventDmo(eventDmo: AtomChangeEventDmo) = {
    val event = new AtomChangeEvent
    event.setId(eventDmo.getId)
    event.setAtomId(eventDmo.getAtomId)
    event.setAtomOrigin(eventDmo.getAtomOrigin)
    event.setAtomType(eventDmo.getAtomType)
    event
  }

  def fromAtomChangeEventDmos(eventDmos: Traversable[AtomChangeEventDmo]) = eventDmos.map(fromAtomChangeEventDmo)
}