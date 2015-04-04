package nl.haploid.event.channel.repository

import nl.haploid.event.channel.AbstractIT
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.TestContextManager

class RowChangeEventRepositoryIT extends AbstractIT {

  @Autowired val repository: RowChangeEventRepository = null

  setup

  "Row change event repository" should "find all events" in {
    repository should not be null
    repository.findAll should not be null
  }
}
