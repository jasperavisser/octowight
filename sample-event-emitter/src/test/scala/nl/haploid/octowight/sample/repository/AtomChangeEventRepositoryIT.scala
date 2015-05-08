package nl.haploid.octowight.sample.repository

import nl.haploid.octowight.sample.AbstractIT
import nl.haploid.octowight.sample.TestData
import org.junit.Test
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import java.util.List
import org.junit.Assert.assertEquals

@RunWith(classOf[JUnitRunner])
// TODO: transactional
class AtomChangeEventRepositoryIT extends AbstractIT {

  @Autowired private val atomChangeEventDmoRepository: AtomChangeEventDmoRepository = null

  override def beforeEach() = {
    super.beforeEach()
    atomChangeEventDmoRepository.deleteAll()
  }

  "Atom change event repository" should "find all events" in {
    val event: AtomChangeEventDmo = TestData.atomChangeEventDmo
    atomChangeEventDmoRepository.saveAndFlush(event)
    val events = atomChangeEventDmoRepository.findAll
    events should have size 1
  }
}
