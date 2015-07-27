package nl.haploid.octowight.builder.service

import nl.haploid.octowight.JsonMapper
import nl.haploid.octowight.kafka.KafkaConsumerFactory
import nl.haploid.octowight.registry.data.ResourceIdentifier
import org.springframework.beans.factory.annotation.{Autowired, Value}
import org.springframework.stereotype.Service

@Service
class DirtyResourceConsumerService {
  @Value("${octowight.kafka.topic.resources.dirty}") private[this] val topic: String = null
  @Value("${octowight.kafka.limit.resources.dirty}") private[this] val limit: Int = 0
  @Autowired private[this] val kafkaConsumerFactory: KafkaConsumerFactory = null
  @Autowired private[this] val jsonMapper: JsonMapper = null

  lazy val kafkaConsumer = kafkaConsumerFactory.kafkaConsumer(topic)

  def commit(): Unit = kafkaConsumer.commit()

  def consumeResourceIdentifiers(): Map[String, Iterable[ResourceIdentifier]] = {
    kafkaConsumer.nextMessages(limit)
      .map(m => jsonMapper.deserialize(m, classOf[ResourceIdentifier]))
      .groupBy(id => id.collection)
  }
}
