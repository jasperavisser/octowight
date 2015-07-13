package nl.haploid.octowight.builder

import nl.haploid.octowight.registry.data.{ResourceMessage, Atom, ResourceRoot}
import nl.haploid.octowight.TestData
import org.springframework.stereotype.Component

@Component
class MockResourceBuilder extends ResourceBuilder {

  override lazy val collection: String = TestData.nextString

  override def build(resourceRoots: Iterable[ResourceRoot]): Iterable[(ResourceMessage, Iterable[Atom])] = Iterable()
}
