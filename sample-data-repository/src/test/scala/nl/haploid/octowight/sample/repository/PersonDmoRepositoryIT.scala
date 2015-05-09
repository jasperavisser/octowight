package nl.haploid.octowight.sample.repository

import javax.persistence.{EntityManager, PersistenceContext}

import nl.haploid.octowight.sample.{AbstractTransactionalIT, TestData}
import org.springframework.beans.factory.annotation.Autowired

class PersonDmoRepositoryIT extends AbstractTransactionalIT {
  @Autowired private val personDmoRepository: PersonDmoRepository = null
  @Autowired private val roleDmoRepository: RoleDmoRepository = null
  @PersistenceContext private val entityManager: EntityManager = null

  "Person DMO repository" should "find all" in {
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