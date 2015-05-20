package nl.haploid.octowight.sample.controller

import java.util

import nl.haploid.octowight.registry.data.Atom
import nl.haploid.octowight.registry.repository.{AtomDmo, ResourceRootDmo, ResourceRootDmoRepository}
import nl.haploid.octowight.sample.data.CaptainResource
import nl.haploid.octowight.sample.repository.{PersonDmoRepository, RoleDmo, RoleDmoRepository}
import nl.haploid.octowight.sample.{AbstractTransactionalIT, TestData}
import org.hamcrest.Matchers
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders

class CaptainControllerIT extends AbstractTransactionalIT {
  @Autowired private[this] val controller: CaptainController = null
  @Autowired private[this] val personDmoRepository: PersonDmoRepository = null
  @Autowired private[this] val resourceRootDmoRepository: ResourceRootDmoRepository = null
  @Autowired private[this] val roleDmoRepository: RoleDmoRepository = null

  private[this] val tempResourceRootDmos = new util.ArrayList[ResourceRootDmo]
  private[this] var mockMvc: MockMvc = null

  override def beforeEach() = {
    super.beforeEach()
    this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build
    roleDmoRepository.deleteAllInBatch()
    personDmoRepository.deleteAllInBatch()
  }

  override def afterEach() = {
    resourceRootDmoRepository.delete(tempResourceRootDmos)
    super.afterEach()
  }

  "Captain controller" should "get a captain" in {
    val personDmo = personDmoRepository.saveAndFlush(TestData.personDmo)
    val roleDmo = roleDmoRepository.saveAndFlush(TestData.roleDmo(personDmo, CaptainResource.ResourceCollection))
    val resourceRootDmo = new ResourceRootDmo(
      root = AtomDmo(new Atom(roleDmo.getId, RoleDmo.AtomCategory, "the seven seas")),
      resourceId = TestData.nextLong,
      resourceCollection = CaptainResource.ResourceCollection,
      version = TestData.nextLong)
    val resourceRootDmoWithId = resourceRootDmoRepository.save(resourceRootDmo)
    tempResourceRootDmos.add(resourceRootDmoWithId)
    mockMvc.perform(MockMvcRequestBuilders.get(s"/captain/${resourceRootDmoWithId.resourceId}"))
      .andExpect(MockMvcResultMatchers.status.isOk)
      .andExpect(MockMvcResultMatchers.content.contentType(MediaType.APPLICATION_JSON))
      .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(personDmo.getName)))
  }
}
