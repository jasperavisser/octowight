package nl.haploid.resource.detector.service;

import nl.haploid.resource.detector.AbstractIT;
import nl.haploid.resource.detector.TestData;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class EventConsumerServiceIT extends AbstractIT {

    @Autowired
    private EventConsumerService service;

    @Autowired
    private KafkaProducer<String, String> kafkaProducer;

    @Rule
    public Timeout globalTimeout = new Timeout(10000);

    @Test
    public void testConsumeMessage() throws InterruptedException, ExecutionException {
        final String topic = TestData.topic();
        service.setTopic(topic);
        final String expectedMessage = UUID.randomUUID().toString();
        final ProducerRecord<String, String> record = new ProducerRecord<>(topic, expectedMessage);
        kafkaProducer.send(record).get();
        final String actualMessage = service.consumeMessage();
        Assert.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void testConsumeMessages() throws InterruptedException, ExecutionException {
        final String topic = TestData.topic();
        service.setTopic(topic);
        final String message1 = UUID.randomUUID().toString();
        final String message2 = UUID.randomUUID().toString();
        final List<String> expectedMessages = Arrays.asList(message1, message2);
        final ProducerRecord<String, String> record1 = new ProducerRecord<>(topic, message1);
        final ProducerRecord<String, String> record2 = new ProducerRecord<>(topic, message2);
        kafkaProducer.send(record1).get();
        kafkaProducer.send(record2).get();
        final List<String> actualMessages = service.consumeMessages(expectedMessages.size() + 1);
        Assert.assertEquals(expectedMessages, actualMessages);
    }
}
