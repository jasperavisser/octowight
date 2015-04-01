package nl.haploid.event.channel

import nl.haploid.event.channel.repository.RowChangeEventRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class EventChannelService @Autowired() (repository: RowChangeEventRepository) {

  val log = LoggerFactory.getLogger(getClass)

  @Scheduled(fixedRate = 500)
  @Transactional
  def queueRowChangeEvents: Unit = {
    log.debug("Queue row change events")
    val events = repository.findAll
    log.debug("Found %d row change events".format(events.size))
    // TODO: write to queue
    repository.delete(events)
  }
}
