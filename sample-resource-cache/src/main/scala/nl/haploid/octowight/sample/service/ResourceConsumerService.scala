package nl.haploid.octowight.sample.service

import nl.haploid.octowight.JsonMapper
import nl.haploid.octowight.kafka.KafkaConsumerFactory
import nl.haploid.octowight.registry.data.ResourceMessage
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.{Autowired, Value}
import org.springframework.stereotype.Service

@Service
class ResourceConsumerService {
  private[this] lazy val log = LoggerFactory.getLogger(getClass)

  @Value("${octowight.kafka.topic.resources.built}") private[this] val topic: String = null
  @Value("${octowight.kafka.limit.resources.built}") private[this] val limit: Int = 0

  @Autowired private[this] val kafkaConsumerFactory: KafkaConsumerFactory = null
  @Autowired private[this] val jsonMapper: JsonMapper = null

  lazy val kafkaConsumer = kafkaConsumerFactory.kafkaConsumer(topic)

  def commit(): Unit = kafkaConsumer.commit()

  def consumeResourceMessages(): Iterable[ResourceMessage] = {
    kafkaConsumer.nextMessages(limit)
      .map(m => jsonMapper.deserialize(m, classOf[ResourceMessage]))
  }
}
