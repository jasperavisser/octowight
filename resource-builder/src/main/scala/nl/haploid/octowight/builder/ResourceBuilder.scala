package nl.haploid.octowight.builder

import nl.haploid.octowight.registry.data.{ResourceMessage, Atom, ResourceRoot}

trait ResourceBuilder {

  def collection: String

  def build(resourceRoots: Iterable[ResourceRoot]): Iterable[(ResourceMessage, Iterable[Atom])]
}
