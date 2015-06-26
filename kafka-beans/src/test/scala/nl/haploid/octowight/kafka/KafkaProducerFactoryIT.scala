package nl.haploid.octowight.kafka

import nl.haploid.octowight.AbstractIT
import org.springframework.beans.factory.annotation.Autowired

class KafkaProducerFactoryIT extends AbstractIT {
  @Autowired private[this] val kafkaProducerFactory: KafkaProducerFactory = null

  it should "build a kafka producer" in {
    kafkaProducerFactory.kafkaProducer should not be null
  }
}
