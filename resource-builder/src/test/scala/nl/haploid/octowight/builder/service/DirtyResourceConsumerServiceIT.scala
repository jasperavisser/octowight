package nl.haploid.octowight.builder.service

import nl.haploid.octowight.kafka.KafkaProducerFactory
import nl.haploid.octowight.registry.data.ResourceIdentifier
import nl.haploid.octowight.{AbstractIT, JsonMapper, TestData}
import org.apache.kafka.clients.producer.ProducerRecord
import org.springframework.beans.factory.annotation.{Autowired, Value}

class DirtyResourceConsumerServiceIT extends AbstractIT {
  @Autowired private[this] val dirtyResourceConsumerService: DirtyResourceConsumerService = null
  @Autowired private[this] val kafkaProducerFactory: KafkaProducerFactory = null
  @Autowired private[this] val jsonMapper: JsonMapper = null
  @Value("${octowight.kafka.topic.resources.dirty}") private[this] val topic: String = null

  behavior of "Dirty resource consumer service"

  it should "consume resource identifiers" in {
    val resourceIdentifier1 = new ResourceIdentifier(collection = TestData.nextString, id = TestData.nextLong)
    val resourceIdentifier2 = new ResourceIdentifier(collection = TestData.nextString, id = TestData.nextLong)
    val message1 = jsonMapper.serialize(resourceIdentifier1)
    val message2 = jsonMapper.serialize(resourceIdentifier2)
    val kafkaProducer = kafkaProducerFactory.kafkaProducer
    kafkaProducer.send(new ProducerRecord[String, String](topic, message1))
    kafkaProducer.send(new ProducerRecord[String, String](topic, message2))
    val resourceIdentifiers = dirtyResourceConsumerService.consumeResourceIdentifiers()
    resourceIdentifiers should have size 2
  }
}
