package nl.haploid.octowight.service;

import mockit.Injectable;
import mockit.StrictExpectations;
import mockit.Tested;
import nl.haploid.octowight.AtomChangeEvent;
import nl.haploid.octowight.JsonMapper;
import nl.haploid.octowight.TestData;
import nl.haploid.octowight.repository.AtomChangeEventDmo;
import nl.haploid.octowight.repository.AtomChangeEventDmoRepository;
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
	private AtomChangeEventFactory eventFactory;

	@Injectable
	private KafkaProducer<String, String> kafkaProducer;

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
			eventFactory.fromAtomChangeEventDmos(expectedEventDmos);
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
