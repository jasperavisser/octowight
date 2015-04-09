package nl.haploid.resource.detector.service;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EventConsumerService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Value("${kafka.topic}")
    private String topic;

    @Autowired
    private ConsumerConnector kafkaConsumer;

    public String consumeSingleEvent() {
        final Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put(topic, 1);
        final Map<String, List<KafkaStream<byte[], byte[]>>> streamsPerTopic = kafkaConsumer.createMessageStreams(topicCountMap);
        final KafkaStream<byte[], byte[]> stream = streamsPerTopic.get(topic).get(0);
        final ConsumerIterator<byte[], byte[]> iterator = stream.iterator();
        final String message = new String(iterator.next().message());
        log.debug(String.format("Consumed message: %s", message));
        return message;
    }
}
