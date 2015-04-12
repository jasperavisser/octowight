package nl.haploid.event.channel.service;

import mockit.StrictExpectations;
import mockit.Tested;
import nl.haploid.event.RowChangeEvent;
import nl.haploid.event.channel.TestData;
import nl.haploid.event.channel.repository.RowChangeEventDmo;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DmoToMessageMapperServiceTest {

	@Tested
	private DmoToMessageMapperService mapperService;

	@Test
	public void testMapSingle() {
		final RowChangeEventDmo eventDmo = TestData.rowChangeEventDmo();
		final RowChangeEvent event = mapperService.map(eventDmo);
		assertEquals(eventDmo.getId(), event.getId());
		assertEquals(eventDmo.getRowId(), event.getRowId());
		assertEquals(eventDmo.getTableName(), event.getTableName());
	}

	@Test
	public void testMapMultiple() {
		final RowChangeEventDmo eventDmo1 = TestData.rowChangeEventDmo();
		final RowChangeEventDmo eventDmo2 = TestData.rowChangeEventDmo();
		final RowChangeEvent event1 = TestData.rowChangeEvent();
		final RowChangeEvent event2 = TestData.rowChangeEvent();
		final List<RowChangeEventDmo> eventDmos = Arrays.asList(eventDmo1, eventDmo2);
		final List<RowChangeEvent> expectedEvents = Arrays.asList(event1, event2);
		new StrictExpectations(mapperService) {{
			mapperService.map(eventDmo1);
			times = 1;
			result = event1;
			mapperService.map(eventDmo2);
			times = 1;
			result = event2;
		}};
		final List<RowChangeEvent> actualEvents = mapperService.map(eventDmos);
		assertEquals(expectedEvents, actualEvents);
	}
}
