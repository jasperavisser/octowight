package nl.haploid.resource.detector.service;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class KafkaConsumerFactoryService {

    @Autowired
    private ConsumerConfig consumerConfig;

    // TODO: test
    public ConsumerConnector createKafkaConsumer() {
        return Consumer.createJavaConsumerConnector(consumerConfig);
    }

    public KafkaStream<byte[], byte[]> createStream(final ConsumerConnector kafkaConsumer, final String topic) {
        final Map<String, Integer> topicCountMap = new HashMap<>();
        topicCountMap.put(topic, 1);
        final Map<String, List<KafkaStream<byte[], byte[]>>> streamsPerTopic = kafkaConsumer.createMessageStreams(topicCountMap);
        return streamsPerTopic.get(topic).get(0);
    }
}