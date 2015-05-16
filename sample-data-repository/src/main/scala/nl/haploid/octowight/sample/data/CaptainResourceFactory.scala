package nl.haploid.octowight.sample.data

import nl.haploid.octowight.registry.data.{ResourceFactory, ResourceRoot}
import nl.haploid.octowight.sample.repository.RoleDmoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class CaptainResourceFactory extends ResourceFactory[CaptainResource] {
  @Autowired private[this] val roleDmoRepository: RoleDmoRepository = null

  @Transactional(readOnly = true)
  def fromResourceRoot(resourceRoot: ResourceRoot): Option[CaptainResource] = {
    Option(roleDmoRepository.findOne(resourceRoot.root.id)) match {
      case Some(roleDmo) =>
        roleDmo.setOrigin(resourceRoot.root.origin)
        val personDmo = roleDmo.getPerson
        personDmo.setOrigin(resourceRoot.root.origin)
        val captainResource = new CaptainResource
        captainResource.setId(resourceRoot.resourceId)
        captainResource.setPersonDmo(personDmo)
        captainResource.setRoleDmo(roleDmo)
        captainResource.setVersion(resourceRoot.version)
        Some(captainResource)
      case None => None
    }
  }
}
