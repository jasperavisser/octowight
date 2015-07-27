package nl.haploid.octowight.builder.service

import java.util.concurrent.{Future, TimeUnit}

import nl.haploid.octowight.JsonMapper
import nl.haploid.octowight.kafka.producer.KafkaOutChannel
import nl.haploid.octowight.registry.data.ResourceMessage
import org.apache.kafka.clients.producer.{ProducerRecord, RecordMetadata}
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.{Autowired, Value}
import org.springframework.stereotype.Service

@Service
class ResourceProducerService extends KafkaOutChannel {
  private[this] lazy val log = LoggerFactory.getLogger(getClass)

  @Value("${octowight.kafka.topic.resources.built}") val topic: String = null
  @Autowired private[this] val jsonMapper: JsonMapper = null

  def send(resources: Iterable[ResourceMessage]): Unit = {
    val futures: Iterable[Future[RecordMetadata]] = resources map {
      resource => {
        val message = jsonMapper.serialize(resource)
        log.debug(s"Send message to $topic: $message")
        kafkaProducer.send(new ProducerRecord[String, String](topic, message))
      }
    }
    futures.map(_.get(5, TimeUnit.SECONDS))
  }
}
