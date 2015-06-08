package nl.haploid.octowight.sample.service

import java.util.concurrent.{Future, TimeUnit}

import nl.haploid.octowight.JsonMapper
import nl.haploid.octowight.kafka.KafkaProducerFactory
import nl.haploid.octowight.sample.Resource
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord, RecordMetadata}
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.{Autowired, Value}
import org.springframework.stereotype.Service

@Service
class ResourceProducerService {
  private[this] lazy val log = LoggerFactory.getLogger(getClass)

  @Value("${octowight.kafka.topic.resources.built}") val topic: String = null
  @Autowired private[this] val jsonMapper: JsonMapper = null
  @Autowired private[this] val kafkaProducerFactory: KafkaProducerFactory = null

  private[this] lazy val kafkaProducer: KafkaProducer[String, String] =
    kafkaProducerFactory.kafkaProducer

  def send(resources: Iterable[Resource]): Unit = {
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
