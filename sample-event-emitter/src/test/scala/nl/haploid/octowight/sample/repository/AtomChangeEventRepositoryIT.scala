package nl.haploid.octowight.sample.repository

import nl.haploid.octowight.sample.{AbstractIT, TestData}
import org.springframework.beans.factory.annotation.Autowired

class AtomChangeEventRepositoryIT extends AbstractIT {
  @Autowired private[this] val atomChangeEventDmoRepository: AtomChangeEventDmoRepository = null

  "Atom change event repository" should "find all events" in {
    atomChangeEventDmoRepository.deleteAll()
    val event = TestData.atomChangeEventDmo
    atomChangeEventDmoRepository.saveAndFlush(event)
    val events = atomChangeEventDmoRepository.findAll
    events should have size 1
  }
}
