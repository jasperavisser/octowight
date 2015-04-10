package nl.haploid.resource.detector.service;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.ConsumerTimeoutException;
import kafka.consumer.KafkaStream;
import kafka.message.MessageAndMetadata;
import mockit.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class EventConsumerServiceTest {

    @Tested
    private EventConsumerService consumerService;

    @Injectable
    private KafkaStream<byte[], byte[]> stream;

    @Test
    public void testConsumeSingleEvent(final @Mocked ConsumerIterator<byte[], byte[]> iterator,
                                       final @Mocked MessageAndMetadata<byte[], byte[]> messageAndMetaData,
                                       final @Injectable("my-topic") String topic)
            throws InterruptedException, ExecutionException {
        final String expectedMessage = UUID.randomUUID().toString();
        new Expectations() {
            {
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

    @Test
    public void testConsumeMultipleEvents(final @Mocked ConsumerIterator<byte[], byte[]> iterator,
                                          final @Mocked MessageAndMetadata<byte[], byte[]> messageAndMetaData,
                                          final @Injectable("my-topic") String topic)
            throws InterruptedException, ExecutionException {
        final String message1 = UUID.randomUUID().toString();
        final String message2 = UUID.randomUUID().toString();
        final List<String> expectedMessages = Arrays.asList(message1, message2);
        new StrictExpectations() {
            {
                stream.iterator();
                times = 1;
                result = iterator;
                iterator.next();
                times = 1;
                result = messageAndMetaData;
                messageAndMetaData.message();
                times = 1;
                result = message1.getBytes();
                iterator.next();
                times = 1;
                result = messageAndMetaData;
                messageAndMetaData.message();
                times = 1;
                result = message2.getBytes();
                iterator.next();
                times = 1;
                result = new ConsumerTimeoutException();
            }
        };
        final List<String> actualMessages = consumerService.consumeMultipleEvents();
        Assert.assertEquals(expectedMessages, actualMessages);
    }
}
