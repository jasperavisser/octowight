package nl.haploid.octowight.service

import java.util.concurrent.{Future, TimeUnit}

import nl.haploid.octowight.JsonMapper
import nl.haploid.octowight.kafka.KafkaProducerFactory
import nl.haploid.octowight.registry.data.{ResourceIdentifier, ResourceRoot}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord, RecordMetadata}
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.{Autowired, Value}
import org.springframework.stereotype.Service

@Service
class DirtyResourceProducerService {
  private[this] lazy val log = LoggerFactory.getLogger(getClass)

  @Value("${octowight.kafka.topic.resources.dirty}") private[this] val topic: String = null
  @Autowired private[this] val jsonMapper: JsonMapper = null
  @Autowired private[this] val kafkaProducerFactory: KafkaProducerFactory = null

  private[this] lazy val kafkaProducer: KafkaProducer[String, String] =
    kafkaProducerFactory.kafkaProducer

  def sendDirtyResource(resourceRoot: ResourceRoot) = {
    val resourceIdentifier = new ResourceIdentifier(collection = resourceRoot.resourceCollection, id = resourceRoot.resourceId)
    val message = jsonMapper.serialize(resourceIdentifier)
    log.debug(s"Send message to $topic: $message")
    kafkaProducer.send(new ProducerRecord[String, String](topic, message))
  }

  def resolveFuture(future: Future[RecordMetadata]) = future.get(5, TimeUnit.SECONDS)
}
