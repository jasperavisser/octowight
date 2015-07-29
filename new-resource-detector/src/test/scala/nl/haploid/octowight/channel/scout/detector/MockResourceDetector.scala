package nl.haploid.octowight.channel.scout.detector

import nl.haploid.octowight.AtomGroup
import nl.haploid.octowight.registry.data.{Atom, ResourceRoot}
import org.springframework.stereotype.Component

object MockResourceDetector {
  val AtomCategory = "john"
  val ResourceCollection = "preston"
}

@Component
class MockResourceDetector extends ResourceDetector {

  override def atomCategories = Set(MockResourceDetector.AtomCategory)

  override def detect(atomGroup: AtomGroup, atomIds: Set[Long]): Set[ResourceRoot] =
    atomIds.map(atomId =>
      new ResourceRoot(
        resourceId = null, resourceCollection = MockResourceDetector.ResourceCollection, version = null,
        root = Atom(id = atomId, group = atomGroup)
      )
    )
}
