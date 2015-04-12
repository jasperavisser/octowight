package nl.haploid.resource.detector.service;

import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import nl.haploid.resource.detector.KafkaConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class EventConsumerService {

    @Value("${kafka.topic}")
    private String topic;

    @Autowired
    private ConsumerConnector kafkaConsumer;

    @Autowired
    private KafkaConfiguration kafkaConfiguration;

    private KafkaStream<byte[], byte[]> stream;

    protected KafkaStream<byte[], byte[]> getStream() {
        if (stream == null) {
            stream = createStream();
        }
        return stream;
    }

    private KafkaStream<byte[], byte[]> createStream() {
        final Map<String, Integer> topicCountMap = new HashMap<>();
        topicCountMap.put(topic, 1);
        final Map<String, List<KafkaStream<byte[], byte[]>>> streamsPerTopic = kafkaConsumer.createMessageStreams(topicCountMap);
        return streamsPerTopic.get(topic).get(0);
    }

    public List<String> consumeMultipleMessages(final int batchSize) {
        return StreamSupport.stream(new KafkaStreamSpliterator(getStream()), false)
                .limit(batchSize)
                .map(messageAndMetadata -> new String(messageAndMetadata.message()))
                .collect(Collectors.toList());
    }

    public String consumeSingleMessage() {
        return new String(getStream().iterator().next().message());
    }
}
