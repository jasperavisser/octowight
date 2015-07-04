package nl.haploid.octowight.registry.repository

import nl.haploid.octowight.registry.{AbstractIT, TestData}
import org.springframework.beans.factory.annotation.Autowired

class ResourceModelDmoRepositoryIT extends AbstractIT {
  @Autowired private[this] val resourceModelDmoRepository: ResourceModelDmoRepository = null

  behavior of "Resource model repository"

  it should "find one" in {
    val expectedResourceModelDmo = TestData.resourceModelDmo
    resourceModelDmoRepository.save(expectedResourceModelDmo)
    val actualResourceModelDmo = resourceModelDmoRepository.findOne(expectedResourceModelDmo.id)
    actualResourceModelDmo.body should be(expectedResourceModelDmo.body)
  }
}
