package nl.haploid.event.channel.service;

import mockit.Injectable;
import mockit.StrictExpectations;
import mockit.Tested;
import nl.haploid.event.JsonMapper;
import nl.haploid.event.AtomChangeEvent;
import nl.haploid.event.channel.TestData;
import nl.haploid.event.channel.repository.AtomChangeEventDmo;
import nl.haploid.event.channel.repository.AtomChangeEventDmoRepository;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class EventChannelServiceTest {

	@Tested
	private EventChannelService service;

	@Injectable
	private AtomChangeEventDmoRepository mockRepository;

	@Injectable
	private KafkaProducer<String, String> kafkaProducer;

	@Injectable
	private DmoToMessageMapperService mapperService;

	@Injectable
	private JsonMapper jsonService;

	@Test
	public void testQueueAtomChangeEvents() throws Exception {
		final List<AtomChangeEventDmo> expectedEventDmos = new ArrayList<>();
		expectedEventDmos.add(TestData.atomChangeEventDmo());
		expectedEventDmos.add(TestData.atomChangeEventDmo());
		expectedEventDmos.add(TestData.atomChangeEventDmo());
		final List<AtomChangeEvent> expectedEvents = new ArrayList<>();
		expectedEvents.add(TestData.atomChangeEvent());
		expectedEvents.add(TestData.atomChangeEvent());
		expectedEvents.add(TestData.atomChangeEvent());
		new StrictExpectations() {{
			mockRepository.findAll();
			times = 1;
			result = expectedEventDmos;
			mapperService.map(expectedEventDmos);
			times = 1;
			result = expectedEvents;
			kafkaProducer.send((ProducerRecord<String, String>) any);
			times = 3;
			mockRepository.delete(expectedEventDmos);
			times = 1;
		}};
		service.queueAtomChangeEvents();
	}
}
