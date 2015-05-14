package nl.haploid.octowight.registry.data

trait ResourceFactory[R <: Resource[_]] {

  def fromResourceRoot(resourceRoot: ResourceRoot): Option[R]
}
