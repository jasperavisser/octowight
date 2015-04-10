package nl.haploid.resource.detector;

import nl.haploid.resource.detector.service.EventConsumerService;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class EventConsumerServiceIT extends AbstractIT {

    @Autowired
    private EventConsumerService service;

    @Autowired
    private KafkaProducer<String, String> kafkaProducer;

    @Value("${kafka.topic}")
    private String topic;

    @Rule
    public Timeout globalTimeout = new Timeout(10000);

    @Test
    public void testConsumeSingleEvent() throws InterruptedException, ExecutionException {
        final String expectedMessage = UUID.randomUUID().toString();
        final ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, expectedMessage);
        kafkaProducer.send(record).get();
        final String actualMessage = service.consumeSingleEvent();
        Assert.assertEquals(expectedMessage, actualMessage);
    }
}
