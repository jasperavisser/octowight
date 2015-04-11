package nl.haploid.resource.detector.service;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Service
public class KafkaConsumerService {

    @Value("${zookeeper.hostname:localhost}")
    private String zookeeperHostname;

    @Value("${zookeeper.port:2181}")
    private int zookeeperPort;

    public int getZookeeperPort() {
        return zookeeperPort;
    }

    public String getZookeeperHostname() {
        return zookeeperHostname;
    }

    // TODO: what problem was i trying to solve here?
    // TODO: solution was to make this service wrap around consumer & streams
    // TODO: with a method to reset (shutdown consumer and make a new one)
    // TODO: it was something i thought of while thinking of how to IT huxtable resource detector
    // TODO: ah yes, commitOffsets

    private ConsumerConnector kafkaConsumer() {
        final Properties properties = new Properties();
        properties.put("zookeeper.connect", String.format("%s:%d", getZookeeperHostname(), getZookeeperPort()));
        properties.put("group.id", "1");
        properties.put("zookeeper.session.timeout.ms", "400");
        properties.put("zookeeper.sync.time.ms", "200");
        properties.put("auto.commit.interval.ms", "1000");
        properties.put("auto.offset.reset", "smallest");
        properties.put("consumer.timeout.ms", "1000");
        return Consumer.createJavaConsumerConnector(new ConsumerConfig(properties));
    }
}
