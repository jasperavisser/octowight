package nl.haploid.event.channel.repository

import nl.haploid.event.channel.AbstractIT
import org.springframework.beans.factory.annotation.Autowired

class RowChangeEventRepositoryIT extends AbstractIT {

  @Autowired val repository: RowChangeEventRepository = null

  "Row change event repository" should "find all events" in {
    repository.findAll should not be null
  }
}
