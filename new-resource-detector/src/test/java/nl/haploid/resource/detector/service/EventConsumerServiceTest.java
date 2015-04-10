package nl.haploid.resource.detector.service;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Tested;
import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class EventConsumerServiceTest {

    @Tested
    private EventConsumerService consumerService;

    @Injectable
    private ConsumerConnector kafkaConsumer;

    @Test
    public void testConsumeSingleEvent(final @Mocked KafkaStream<byte[], byte[]> stream, final @Mocked ConsumerIterator<byte[], byte[]> iterator,
                                       final @Mocked MessageAndMetadata<byte[], byte[]> messageAndMetaData, final @Injectable("my-topic") String topic)
            throws InterruptedException, ExecutionException {
        final String expectedMessage = UUID.randomUUID().toString();
        new Expectations(consumerService) {
            {
                consumerService.createStream(topic);
                times = 1;
                result = stream;
                stream.iterator();
                times = 1;
                result = iterator;
                iterator.next();
                times = 1;
                result = messageAndMetaData;
                messageAndMetaData.message();
                times = 1;
                result = expectedMessage.getBytes();
            }
        };
        final String actualMessage = consumerService.consumeSingleEvent();
        Assert.assertEquals(expectedMessage, actualMessage);
    }
}
