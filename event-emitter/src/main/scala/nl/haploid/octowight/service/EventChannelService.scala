package nl.haploid.octowight.service

import java.util.concurrent.{Future, TimeUnit}

import nl.haploid.octowight.{AtomChangeEvent, JsonMapper}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord, RecordMetadata}
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.{Autowired, Value}
import org.springframework.stereotype.Service

@Service
class EventChannelService {
  private final val log =  LoggerFactory.getLogger(getClass)

  @Value("${octowight.kafka.topic.events}") private val topic: String = null

  @Autowired private val kafkaProducer: KafkaProducer[String, String] = null
  @Autowired private val jsonMapper: JsonMapper = null

  def sendEvents(events: Traversable[AtomChangeEvent]) = {
    log.debug(s"Send ${events.size} messages")
    events.toList
      .map(sendEvent)
      .map(resolveFuture)
  }

  def sendEvent(event: AtomChangeEvent) = {
    val message = jsonMapper.serialize(event)
    log.debug(s"Send message to $topic: $message")
    val record = new ProducerRecord[String, String](topic, message)
    kafkaProducer.send(record)
  }

  private def resolveFuture(future: Future[RecordMetadata]) = {
    future.get(5, TimeUnit.SECONDS)
  }
}
