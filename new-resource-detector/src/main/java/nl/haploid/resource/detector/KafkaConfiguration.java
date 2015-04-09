package nl.haploid.resource.detector;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.javaapi.consumer.ConsumerConnector;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class KafkaConfiguration {
    @Value("${kafka.hostname:localhost}")
    private String hostname;

    @Value("${kafka.port:9092}")
    private int port;

    @Value("${zookeeper.hostname:localhost}")
    private String zookeeperHostname;

    @Value("${zookeeper.port:2181}")
    private int zookeeperPort;

    public String getHostname() {
        return hostname;
    }

    public int getPort() {
        return port;
    }

    public String getZookeeperHostname() {
        return zookeeperHostname;
    }

    public int getZookeeperPort() {
        return zookeeperPort;
    }

    @Bean
    public ConsumerConnector kafkaConsumer() {
        Properties properties = new Properties();
        properties.put("zookeeper.connect", String.format("%s:%d", getZookeeperHostname(), getZookeeperPort()));
        properties.put("group.id", "1");
        properties.put("zookeeper.session.timeout.ms", "400");
        properties.put("zookeeper.sync.time.ms", "200");
        properties.put("auto.commit.interval.ms", "1000");
        return Consumer.createJavaConsumerConnector(new ConsumerConfig(properties));
    }

    @Bean
    public KafkaProducer<String, String> kafkaProducer() {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, String.format("%s:%d", getHostname(), getPort()));
        properties.put(ProducerConfig.RETRIES_CONFIG, "3");
        properties.put(ProducerConfig.ACKS_CONFIG, "all");
        properties.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "none");
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, 200);
        properties.put(ProducerConfig.BLOCK_ON_BUFFER_FULL_CONFIG, true);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new KafkaProducer<String, String>(properties);
    }
}
