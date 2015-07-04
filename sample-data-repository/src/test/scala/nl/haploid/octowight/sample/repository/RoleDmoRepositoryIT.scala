package nl.haploid.octowight.sample.repository

import nl.haploid.octowight.sample.{AbstractTransactionalIT, TestData}
import org.springframework.beans.factory.annotation.Autowired

class RoleDmoRepositoryIT extends AbstractTransactionalIT {
  @Autowired private[this] val personDmoRepository: PersonDmoRepository = null
  @Autowired private[this] val roleDmoRepository: RoleDmoRepository = null

  behavior of "Role repository"

  it should "find all" in {
    roleDmoRepository.deleteAll()
    val personDmo = personDmoRepository.saveAndFlush(TestData.personDmo)
    val roleDmo = TestData.roleDmo(personDmo, "cleaner")
    roleDmoRepository.saveAndFlush(roleDmo)
    val roleDmos = roleDmoRepository.findAll
    roleDmos should have size 1
    roleDmos.get(0).getPerson.getName should be(personDmo.getName)
  }
}
