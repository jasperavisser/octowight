package nl.haploid.octowight.sample.controller

import java.util

import nl.haploid.octowight.registry.repository.{ResourceRootDmo, ResourceRootDmoRepository}
import nl.haploid.octowight.sample.data.CaptainResource
import nl.haploid.octowight.sample.repository.{PersonDmoRepository, RoleDmo, RoleDmoRepository}
import nl.haploid.octowight.sample.{AbstractTransactionalIT, TestData}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders

class CaptainControllerIT extends AbstractTransactionalIT {
  @Autowired private val controller: CaptainController = null
  @Autowired private val personDmoRepository: PersonDmoRepository = null
  @Autowired private val resourceRootDmoRepository: ResourceRootDmoRepository = null
  @Autowired private val roleDmoRepository: RoleDmoRepository = null

  private val tempResourceRootDmos = new util.ArrayList[ResourceRootDmo]
  private var mockMvc: MockMvc = null

  override def beforeEach() {
    super.beforeEach()
    this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build
    roleDmoRepository.deleteAllInBatch()
    personDmoRepository.deleteAllInBatch()
  }

  override def afterEach() {
    resourceRootDmoRepository.delete(tempResourceRootDmos)
  }

  "Captain controller" should "get a captain" in {
    val personDmo = personDmoRepository.saveAndFlush(TestData.personDmo)
    val roleDmo = roleDmoRepository.saveAndFlush(TestData.roleDmo(personDmo, CaptainResource.ResourceType))
    val resourceRootDmo = new ResourceRootDmo
    resourceRootDmo.setAtomId(roleDmo.getId)
    resourceRootDmo.setAtomType(RoleDmo.AtomType)
    resourceRootDmo.setAtomOrigin("the seven seas")
    resourceRootDmo.setResourceId(TestData.nextLong)
    resourceRootDmo.setResourceType(CaptainResource.ResourceType)
    val resourceRootDmoWithId = resourceRootDmoRepository.save(resourceRootDmo)
    tempResourceRootDmos.add(resourceRootDmoWithId)
    mockMvc.perform(MockMvcRequestBuilders.get(s"/captain/${resourceRootDmoWithId.getResourceId}")).andExpect(MockMvcResultMatchers.status.isOk).andExpect(MockMvcResultMatchers.content.contentType(MediaType.APPLICATION_JSON))
  }
}
