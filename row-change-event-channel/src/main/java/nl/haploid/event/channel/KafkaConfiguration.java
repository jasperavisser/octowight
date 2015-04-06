package nl.haploid.event.channel;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class KafkaConfiguration {

    @Bean
    public KafkaProducer<String, String> kafkaProducer() {
        // TODO: start containers
        // TODO: if we always use docker host IP, we don't need to inspect
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "server1:123,server2:456");
        properties.put(ProducerConfig.RETRIES_CONFIG, "3");
        properties.put(ProducerConfig.ACKS_CONFIG, "all");
        properties.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "none");
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, 200);
        properties.put(ProducerConfig.BLOCK_ON_BUFFER_FULL_CONFIG, true);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.ByteArraySerializer");

        // TODO: dockerservice -> not a bean, so we can use it in @Before/AfterClass
        // TODO: if not a bean, then we can't inject @Value for DOCKER_HOST
        // TODO: if not a bean, then how to access it from other beans? singleton? that would be yucky

//        return new KafkaProducer<String, String>(properties);
        return null;
    }
}
