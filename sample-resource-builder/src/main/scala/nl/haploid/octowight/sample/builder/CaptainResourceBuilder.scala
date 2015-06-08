package nl.haploid.octowight.sample.builder

import nl.haploid.octowight.JsonMapper
import nl.haploid.octowight.registry.data.{ResourceIdentifier, Atom, ResourceRoot}
import nl.haploid.octowight.sample.data.CaptainModel
import nl.haploid.octowight.sample.repository.RoleDmoRepository
import nl.haploid.octowight.sample.{ExistingResource, MissingResource, Resource}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional

class CaptainResourceBuilder extends ResourceBuilder {
  @Autowired private[this] val roleDmoRepository: RoleDmoRepository = null
  @Autowired private[this] val jsonMapper: JsonMapper = null

  override val collection = "captain"

  @Transactional(readOnly = true)
  override def build(resourceRoots: Iterable[ResourceRoot]): Iterable[(Resource, Iterable[Atom])] =
    resourceRoots.map(build)

  private[this] def build(resourceRoot: ResourceRoot): (Resource, Iterable[Atom]) = {
    val resourceIdentifier = new ResourceIdentifier(collection = resourceRoot.resourceCollection, id = resourceRoot.resourceId)
    Option(roleDmoRepository.findOne(resourceRoot.root.id)) match {
      case Some(roleDmo) =>
        roleDmo.setOrigin(resourceRoot.root.origin)
        val personDmo = roleDmo.getPerson
        personDmo.setOrigin(resourceRoot.root.origin)
        val model = new CaptainModel(id = personDmo.getId, name = personDmo.getName)
        val resource = new ExistingResource(resourceIdentifier = resourceIdentifier, model = jsonMapper.serialize(model))
        (resource, List(personDmo.toAtom, roleDmo.toAtom))
      case None =>
        val resource = new MissingResource(resourceIdentifier = resourceIdentifier)
        (resource, List())
    }
  }
}
