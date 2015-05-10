package nl.haploid.octowight.detector

import nl.haploid.octowight.AtomChangeEvent
import nl.haploid.octowight.registry.data.{ResourceRoot, ResourceRootFactory}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

object MockResourceDetector {
  val AtomType = "john"
  val ResourceType = "preston"
}

@Component
class MockResourceDetector extends ResourceDetector {
  @Autowired private[this] val resourceRootFactory: ResourceRootFactory = null

  override def getAtomTypes = List(MockResourceDetector.AtomType)

  override def detect(events: Traversable[AtomChangeEvent]): Traversable[ResourceRoot] =
    events.map(resourceRootFactory.fromAtomChangeEvent(_, MockResourceDetector.ResourceType))
}
