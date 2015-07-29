package nl.haploid.octowight.consumer.service

import nl.haploid.octowight.kafka.consumer.KafkaConsumerFactory
import nl.haploid.octowight.{AtomChangeEvent, JsonMapper}
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.{Autowired, Value}
import org.springframework.stereotype.Service

@Service
class EventConsumerService {
  protected[this] lazy val log = LoggerFactory.getLogger(getClass)

  @Autowired private[this] val jsonMapper: JsonMapper = null
  @Autowired private[this] val kafkaConsumerFactory: KafkaConsumerFactory = null
  @Value("${octowight.kafka.topic.events}") var topic: String = null

  private[this] lazy val kafkaConsumer = kafkaConsumerFactory.kafkaConsumer(topic)

  // TODO: make limit configurable
  private[this] lazy val limit = 100

  def commit() = kafkaConsumer.commit()

  def consumeEvent: AtomChangeEvent = parseMessage(kafkaConsumer.nextMessage)

  def consumeDistinctEvents(): Set[AtomChangeEvent] = {
    val events = kafkaConsumer.nextMessages(limit).map(parseMessage)
    log.debug(s"Consumed ${events.size} events")
    events.toSet
  }

  protected def parseMessage(message: String) = {
    log.debug(s"Consume message: $message")
    jsonMapper.deserialize(message, classOf[AtomChangeEvent])
  }
}
