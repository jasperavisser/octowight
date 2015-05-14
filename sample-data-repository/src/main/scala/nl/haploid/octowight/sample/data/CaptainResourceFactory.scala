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
  def fromResourceRoot(resourceRoot: ResourceRoot) = {
    Option(roleDmoRepository.findOne(resourceRoot.getAtomId)) match {
      case Some(roleDmo) =>
        roleDmo.setOrigin(resourceRoot.getAtomOrigin)
        val personDmo = roleDmo.getPerson
        personDmo.setOrigin(resourceRoot.getAtomOrigin)
        val captainResource = new CaptainResource
        captainResource.setId(resourceRoot.getResourceId)
        captainResource.setPersonDmo(personDmo)
        captainResource.setRoleDmo(roleDmo)
        captainResource.setVersion(resourceRoot.getVersion)
        captainResource
      case None =>
        throw new ResourceNotFoundException
    }
  }
}
