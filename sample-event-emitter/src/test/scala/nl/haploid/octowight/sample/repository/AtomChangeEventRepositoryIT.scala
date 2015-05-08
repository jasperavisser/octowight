package nl.haploid.octowight.sample.repository

import nl.haploid.octowight.sample.{AbstractIT, TestData}
import org.springframework.beans.factory.annotation.Autowired

// TODO: transactional
class AtomChangeEventRepositoryIT extends AbstractIT {

  @Autowired private val atomChangeEventDmoRepository: AtomChangeEventDmoRepository = null

  override def beforeEach() = {
    super.beforeEach()
    atomChangeEventDmoRepository.deleteAll()
  }

  "Atom change event repository" should "find all events" in {
    val event = TestData.atomChangeEventDmo
    atomChangeEventDmoRepository.saveAndFlush(event)
    val events = atomChangeEventDmoRepository.findAll
    events should have size 1
  }
}
