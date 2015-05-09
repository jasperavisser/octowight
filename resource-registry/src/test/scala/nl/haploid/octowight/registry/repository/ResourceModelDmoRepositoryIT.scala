package nl.haploid.octowight.registry.repository

import nl.haploid.octowight.registry.{AbstractIT, TestData}
import org.springframework.beans.factory.annotation.Autowired

class ResourceModelDmoRepositoryIT extends AbstractIT {
  @Autowired private val resourceModelDmoRepository: ResourceModelDmoRepository = null

  "Resource model DMO repository" should "find one" in {
    val expectedResourceModelDmo = TestData.resourceModelDmo
    resourceModelDmoRepository.save(expectedResourceModelDmo)
    val actualResourceModelDmo = resourceModelDmoRepository.findOne(expectedResourceModelDmo.getId)
    actualResourceModelDmo.getBody should be(expectedResourceModelDmo.getBody)
  }
}
