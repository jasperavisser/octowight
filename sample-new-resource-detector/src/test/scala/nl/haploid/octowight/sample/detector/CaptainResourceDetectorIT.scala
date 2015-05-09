package nl.haploid.octowight.sample.detector

import java.util
import java.util.Collections
import javax.persistence.{EntityManager, PersistenceContext}

import nl.haploid.octowight.sample.repository.{PersonDmoRepository, RoleDmoRepository}
import nl.haploid.octowight.sample.{AbstractTransactionalIT, TestData}
import org.springframework.beans.factory.annotation.Autowired

class CaptainResourceDetectorIT extends AbstractTransactionalIT {
  @Autowired private val detector: CaptainResourceDetector = null
  @Autowired private val personDmoRepository: PersonDmoRepository = null
  @Autowired private val roleDmoRepository: RoleDmoRepository = null
  @PersistenceContext private val entityManager: EntityManager = null

  "Captain resource detector" should "get roles by id" in {
    val personDmo = personDmoRepository.saveAndFlush(TestData.personDmo)
    val roleDmo1 = roleDmoRepository.saveAndFlush(TestData.roleDmo(personDmo, "first mate"))
    roleDmoRepository.saveAndFlush(TestData.roleDmo(personDmo, "harpooner"))
    val event1 = TestData.atomChangeEvent(roleDmo1.getId)
    val events = Collections.singletonList(event1)
    val dmosById = detector.getRolesById(events)
    dmosById should have size 1
    dmosById.get(roleDmo1.getId).orNull should be(roleDmo1)
  }

  "Captain resource detector" should "detect captains" in {
    val personDmo = personDmoRepository.saveAndFlush(TestData.personDmo)
    val dmo1 = roleDmoRepository.saveAndFlush(TestData.roleDmo(personDmo, CaptainResourceDetector.RoleType))
    val dmo2 = roleDmoRepository.saveAndFlush(TestData.roleDmo(personDmo, "deckhand"))
    entityManager.clear()
    val event1 = TestData.atomChangeEvent(dmo1.getId)
    val event2 = TestData.atomChangeEvent(dmo2.getId)
    val events = util.Arrays.asList(event1, event2)
    val actualResourceRoots = detector.detect(events)
    actualResourceRoots should have size 1
    actualResourceRoots.get(0).getAtomId should be(dmo1.getId)
  }
}