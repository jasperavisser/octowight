package nl.haploid.octowight.sample.builder

import nl.haploid.octowight.registry.data.{Atom, ResourceRoot}
import nl.haploid.octowight.sample.Resource

trait ResourceBuilder {

  def collection: String

  def build(resourceRoots: Iterable[ResourceRoot]): Iterable[(Resource, Iterable[Atom])]
}
