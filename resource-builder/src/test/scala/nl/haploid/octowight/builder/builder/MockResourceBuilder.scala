package nl.haploid.octowight.builder.builder

import nl.haploid.octowight.builder.TestData
import nl.haploid.octowight.registry.data.{Atom, ResourceMessage, ResourceRoot}
import org.springframework.stereotype.Component

@Component
class MockResourceBuilder extends ResourceBuilder {

  override lazy val collection: String = TestData.nextString

  override def build(resourceRoots: Iterable[ResourceRoot]): Iterable[(ResourceMessage, Iterable[Atom])] = Iterable()
}
