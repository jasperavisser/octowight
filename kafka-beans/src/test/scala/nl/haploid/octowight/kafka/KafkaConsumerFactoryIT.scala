package nl.haploid.octowight.kafka

import nl.haploid.octowight.{TestData, AbstractIT}
import org.springframework.beans.factory.annotation.Autowired

class KafkaConsumerFactoryIT extends AbstractIT {
  @Autowired private[this] val kafkaConsumerFactory: KafkaConsumerFactory = null

  private[this] val topic = TestData.nextString

  it should "build a kafka consumer" in {
    kafkaConsumerFactory.kafkaConsumer(topic) should not be null
  }
}
