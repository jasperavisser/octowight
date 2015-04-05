package nl.haploid.event.channel

import grizzled.slf4j.Logging
import nl.haploid.event.channel.repository.RowChangeEventRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class EventChannelService @Autowired()(repository: RowChangeEventRepository) extends Logging {

  @Scheduled(fixedRate = 500)
  @Transactional
  def queueRowChangeEvents: Unit = {
    debug("Queue row change events")
    val events = repository.findAll
    debug("Found %d row change events".format(events.size))
    // TODO: write to queue
    repository.delete(events)
  }
}
