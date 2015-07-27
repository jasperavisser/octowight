package nl.haploid.octowight.builder.builder

import nl.haploid.octowight.registry.data.{Atom, ResourceMessage, ResourceRoot}

trait ResourceBuilder {

  def collection: String

  def build(resourceRoots: Iterable[ResourceRoot]): Iterable[(ResourceMessage, Iterable[Atom])]
}
