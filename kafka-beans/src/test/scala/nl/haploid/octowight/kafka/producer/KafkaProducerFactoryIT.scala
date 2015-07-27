package nl.haploid.octowight.kafka.producer

import nl.haploid.octowight.kafka.AbstractIT
import org.springframework.beans.factory.annotation.Autowired

class KafkaProducerFactoryIT extends AbstractIT {
  @Autowired private[this] val kafkaProducerFactory: KafkaProducerFactory = null

  behavior of "Kafka producer factory"

  it should "build a kafka producer" in {
    kafkaProducerFactory.kafkaProducer should not be null
  }
}
