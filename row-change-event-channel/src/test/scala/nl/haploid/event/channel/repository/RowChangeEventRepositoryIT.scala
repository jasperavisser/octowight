package nl.haploid.event.channel.repository

import nl.haploid.event.channel.AbstractIT
import org.springframework.beans.factory.annotation.Autowired

class RowChangeEventRepositoryIT extends AbstractIT {

  // TODO: make tests run with maven
  // TODO: provide setup for database

  @Autowired val repository: RowChangeEventRepository = null

  setup

  "Row change event repository" should "find all events" in {
    repository should not be null
    repository.findAll should not be null
  }
}
