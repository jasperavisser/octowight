package nl.haploid.octowight.kafka.producer

import org.apache.kafka.clients.producer.KafkaProducer
import org.springframework.beans.factory.annotation.Autowired

trait KafkaOutChannel {
  @Autowired private[this] val kafkaProducerFactory: KafkaProducerFactory = null

  protected[this] lazy val kafkaProducer: KafkaProducer[String, String] =
    kafkaProducerFactory.kafkaProducer
}
