package nl.haploid.event.channel;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class KafkaConfiguration {

    @Bean
    public KafkaProducer<String, String> kafkaProducer() {
        Properties properties = new Properties();
        properties.put("metadata.broker.list", "192.168.1.203:9092");
        properties.put("value.serializer", "kafka.serializer.StringEncoder");
        properties.put("request.required.acks", "1");

        return new KafkaProducer<String, String>(properties);

        //sending...
//        String topic = "test";
//        String message = "Hello Kafka";
//        ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, message);
//        p.send(record);
    }
}
