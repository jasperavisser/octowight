package nl.haploid.event.channel.service;

import mockit.Injectable;
import mockit.StrictExpectations;
import mockit.Tested;
import nl.haploid.event.JsonMapper;
import nl.haploid.event.RowChangeEvent;
import nl.haploid.event.channel.TestData;
import nl.haploid.event.channel.repository.RowChangeEventDmo;
import nl.haploid.event.channel.repository.RowChangeEventDmoRepository;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class EventChannelServiceTest {

	@Tested
	private EventChannelService service;

	@Injectable
	private RowChangeEventDmoRepository mockRepository;

	@Injectable
	private KafkaProducer<String, String> kafkaProducer;

	@Injectable
	private DmoToMessageMapperService mapperService;

	@Injectable
	private JsonMapper jsonService;

	@Test
	public void testQueueRowChangeEvents() throws Exception {
		final List<RowChangeEventDmo> expectedEventDmos = new ArrayList<>();
		expectedEventDmos.add(TestData.rowChangeEventDmo());
		expectedEventDmos.add(TestData.rowChangeEventDmo());
		expectedEventDmos.add(TestData.rowChangeEventDmo());
		final List<RowChangeEvent> expectedEvents = new ArrayList<>();
		expectedEvents.add(TestData.rowChangeEvent());
		expectedEvents.add(TestData.rowChangeEvent());
		expectedEvents.add(TestData.rowChangeEvent());
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
		service.queueRowChangeEvents();
	}
}
