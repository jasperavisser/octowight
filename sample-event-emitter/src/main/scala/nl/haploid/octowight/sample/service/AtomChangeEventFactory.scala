package nl.haploid.octowight.sample.service

import nl.haploid.octowight.AtomChangeEvent
import nl.haploid.octowight.sample.repository.AtomChangeEventDmo
import org.springframework.stereotype.Component
import java.util.Collection
import java.util.List
import java.util.stream.Collectors

@Component
class AtomChangeEventFactory {

  def fromAtomChangeEventDmo(eventDmo: AtomChangeEventDmo): AtomChangeEvent = {
    val event: AtomChangeEvent = new AtomChangeEvent
    event.setId(eventDmo.getId)
    event.setAtomId(eventDmo.getAtomId)
    event.setAtomOrigin(eventDmo.getAtomOrigin)
    event.setAtomType(eventDmo.getAtomType)
    event
  }

  def fromAtomChangeEventDmos(eventDmos: Traversable[AtomChangeEventDmo]) = {
    eventDmos.map(fromAtomChangeEventDmo)
  }
}
