package nl.haploid.octowight.sample.repository

import javax.persistence.{EntityManager, PersistenceContext}

import nl.haploid.octowight.sample.{AbstractTransactionalIT, TestData}
import org.springframework.beans.factory.annotation.Autowired

class PersonDmoRepositoryIT extends AbstractTransactionalIT {
  @Autowired private[this] val personDmoRepository: PersonDmoRepository = null
  @Autowired private[this] val roleDmoRepository: RoleDmoRepository = null
  @PersistenceContext private[this] val entityManager: EntityManager = null

  behavior of "Person repository"

  it should "find all" in {
    roleDmoRepository.deleteAll()
    personDmoRepository.deleteAll()
    val personDmo = personDmoRepository.saveAndFlush(TestData.personDmo)
    roleDmoRepository.saveAndFlush(TestData.roleDmo(personDmo, "cleaner"))
    roleDmoRepository.saveAndFlush(TestData.roleDmo(personDmo, "supervisor"))
    entityManager.clear()
    val personDmos = personDmoRepository.findAll
    personDmos should have size 1
    personDmos.get(0).getRoles should have size 2
  }
}
