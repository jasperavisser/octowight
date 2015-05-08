package nl.haploid.octowight.service

import java.util.concurrent.Future

import nl.haploid.octowight.JsonMapper
import nl.haploid.octowight.registry.data.ResourceRoot
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord, RecordMetadata}
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.{Autowired, Value}
import org.springframework.stereotype.Service

@Service
class DirtyResourceProducerService {
  private val log = LoggerFactory.getLogger(getClass)

  @Value("${octowight.kafka.topic.resources.dirty}") private val topic: String = null

  @Autowired private val kafkaProducer: KafkaProducer[String, String] = null
  @Autowired private val jsonMapper: JsonMapper = null

  def sendDirtyResource(resourceRoot: ResourceRoot) = {
    val message = jsonMapper.serialize(resourceRoot)
    log.debug(s"Send message to $topic: $message")
    kafkaProducer.send(new ProducerRecord[String, String](topic, message))
  }

  // TODO: we don't need this in scala
  def resolveFuture(future: Future[RecordMetadata]) = future.get
}
