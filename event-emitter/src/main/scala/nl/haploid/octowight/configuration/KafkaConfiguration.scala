package nl.haploid.octowight.configuration

import kafka.consumer.ConsumerConfig
import org.springframework.context.annotation.{Bean, Configuration}

@Configuration
class KafkaConfiguration {

  // TODO: this module doesn't have consumers, so it's a little silly to require configuration for one
  @Bean def consumerConfig: ConsumerConfig = null
}
