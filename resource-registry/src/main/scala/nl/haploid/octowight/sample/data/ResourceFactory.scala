package nl.haploid.octowight.sample.data

import nl.haploid.octowight.registry.data.{Resource, ResourceRoot}

trait ResourceFactory[R <: Resource[_]] {

  def fromResourceRoot(resourceRoot: ResourceRoot): R
}
