package nl.haploid.resource.detector.service;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.ConsumerTimeoutException;
import kafka.consumer.KafkaStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventConsumerService {

    private static final int BATCH_SIZE = 100;

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private KafkaStream<byte[], byte[]> stream;

    public List<String> consumeMultipleEvents() {
        final ConsumerIterator<byte[], byte[]> iterator = stream.iterator();
        final List<String> messages = new ArrayList<String>();
        for (int n = 0; n < BATCH_SIZE; n++) {
            try {
                final String message = new String(iterator.next().message());
                messages.add(message);
            } catch (ConsumerTimeoutException e) {
                break;
            }
        }
        return messages;
    }

    public String consumeSingleEvent() {
        final ConsumerIterator<byte[], byte[]> iterator = stream.iterator();
        final String message = new String(iterator.next().message());
        log.debug(String.format("Consumed message: %s", message));
        return message;
    }
}
