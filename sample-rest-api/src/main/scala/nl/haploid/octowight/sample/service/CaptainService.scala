package nl.haploid.octowight.sample.service

import nl.haploid.octowight.sample.data.{CaptainModel, CaptainResource}
import org.springframework.stereotype.Service

@Service
class CaptainService extends AbstractResourceService[CaptainModel, CaptainResource] {

  def getModelClass = classOf[CaptainModel]

  def getResourceType = CaptainResource.ResourceType
}
