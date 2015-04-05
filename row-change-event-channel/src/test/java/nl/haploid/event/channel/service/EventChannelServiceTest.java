package nl.haploid.event.channel.service;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import nl.haploid.event.channel.repository.RowChangeEvent;
import nl.haploid.event.channel.repository.RowChangeEventRepository;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class EventChannelServiceTest {

    @Tested
    private EventChannelService service;

    @Injectable
    private RowChangeEventRepository mockRepository;

    @Injectable
    private KafkaProducer<String, String> kafkaProducer;

    @Test
    public void testQueueRowChangeEvents() {
        final List<RowChangeEvent> expectedEvents = new ArrayList<RowChangeEvent>();
        expectedEvents.add(new RowChangeEvent());
        new Expectations() {
            {
                mockRepository.findAll();
                times = 1;
                result = expectedEvents;
                kafkaProducer.send((ProducerRecord<String, String>) any);
                times = 1;
                mockRepository.delete(expectedEvents);
                times = 1;
            }
        };
        service.queueRowChangeEvents();
    }
}
