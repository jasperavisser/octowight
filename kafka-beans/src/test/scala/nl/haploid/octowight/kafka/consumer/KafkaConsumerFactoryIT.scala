package nl.haploid.octowight.kafka.consumer

import nl.haploid.octowight.kafka.{AbstractIT, TestData}
import org.springframework.beans.factory.annotation.Autowired

class KafkaConsumerFactoryIT extends AbstractIT {
  @Autowired private[this] val kafkaConsumerFactory: KafkaConsumerFactory = null

  private[this] val topic = TestData.nextString

  behavior of "Kafka consumer factory"

  it should "build a kafka consumer" in {
    kafkaConsumerFactory.kafkaConsumer(topic) should not be null
  }
}
