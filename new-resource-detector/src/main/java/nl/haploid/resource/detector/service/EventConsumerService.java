package nl.haploid.resource.detector.service;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.ConsumerTimeoutException;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import nl.haploid.resource.detector.KafkaConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventConsumerService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Value("${kafka.topic}")
    private String topic;

    @Autowired
    private ConsumerConnector kafkaConsumer;

    @Autowired
    private KafkaConfiguration kafkaConfiguration;

    private KafkaStream<byte[], byte[]> stream;

    protected KafkaStream<byte[], byte[]> getStream() {
        if (stream == null) {
            stream = kafkaConfiguration.createStream(kafkaConsumer, topic);
        }
        return stream;
    }

    public List<String> consumeMultipleMessages(final int batchSize) {
        final ConsumerIterator<byte[], byte[]> iterator = getStream().iterator();
        final List<String> messages = new ArrayList<String>();
        for (int n = 0; n < batchSize; n++) {
            try {
                final String message = new String(iterator.next().message());
                messages.add(message);
            } catch (ConsumerTimeoutException e) {
                break;
            }
        }
        // TODO: parse message (here or in separate service?)
        return messages;
    }

    public String consumeSingleMessage() {
        final ConsumerIterator<byte[], byte[]> iterator = getStream().iterator();
        final String message = new String(iterator.next().message());
        log.debug(String.format("Consumed message: %s", message));
        // TODO: parse message (here or in separate service?)
        return message;
    }
}
