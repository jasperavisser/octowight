package nl.haploid.octowight.sample.service

import nl.haploid.octowight.JsonMapper
import nl.haploid.octowight.kafka.KafkaConsumerFactory
import nl.haploid.octowight.sample.{AbstractIT, Resource, TestData}
import org.springframework.beans.factory.annotation.{Autowired, Value}

class ResourceProducerServiceIT extends AbstractIT {
  @Autowired private[this] val resourceProducerService: ResourceProducerService = null
  @Autowired private[this] val kafkaConsumerFactory: KafkaConsumerFactory = null
  @Autowired private[this] val jsonMapper: JsonMapper = null
  @Value("${octowight.kafka.topic.resources.built}") private[this] val topic: String = null

  it should "send" in {
    val resource1 = TestData.resource
    val resource2 = TestData.resource
    val expectedResources: Iterable[Resource] = Iterable(resource1, resource2)
    resourceProducerService.send(expectedResources)
    val kafkaConsumer = kafkaConsumerFactory.kafkaConsumer(topic)
    val actualResources = kafkaConsumer.nextMessages(3)
      .map(jsonMapper.deserialize(_, classOf[Resource]))
    actualResources should be(expectedResources)
  }
}
