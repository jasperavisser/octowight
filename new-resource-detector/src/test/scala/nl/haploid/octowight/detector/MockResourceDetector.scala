package nl.haploid.octowight.detector

import nl.haploid.octowight.AtomChangeEvent
import nl.haploid.octowight.registry.data.ResourceRoot
import org.springframework.stereotype.Component

object MockResourceDetector {
  val AtomCategory = "john"
  val ResourceType = "preston"
}

@Component
class MockResourceDetector extends ResourceDetector {

  override def getAtomCategories = List(MockResourceDetector.AtomCategory)

  override def detect(events: Traversable[AtomChangeEvent]): Traversable[ResourceRoot] =
    events.map(ResourceRoot(_, MockResourceDetector.ResourceType))
}
