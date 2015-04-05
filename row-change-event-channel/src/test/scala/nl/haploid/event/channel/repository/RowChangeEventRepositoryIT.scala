package nl.haploid.event.channel.repository

import nl.haploid.event.channel.{AbstractIT, SlowTest}
import org.springframework.beans.factory.annotation.Autowired

class RowChangeEventRepositoryIT extends AbstractIT {

  @Autowired val repository: RowChangeEventRepository = null

  "Row change event repository" should "be injected" taggedAs SlowTest in {
    setup
    repository should not be null
  }

  "Row change event repository" should "find all events" taggedAs SlowTest in {
    setup
    val event = new RowChangeEvent
    event.tableName = "bob"
    event.rowId = 123
    repository.save(event)
    repository.findAll should have length 1
  }
}
