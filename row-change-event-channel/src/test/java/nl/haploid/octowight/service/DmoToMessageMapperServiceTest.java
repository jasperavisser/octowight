package nl.haploid.octowight.service;

import mockit.StrictExpectations;
import mockit.Tested;
import nl.haploid.octowight.AtomChangeEvent;
import nl.haploid.octowight.TestData;
import nl.haploid.octowight.repository.AtomChangeEventDmo;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DmoToMessageMapperServiceTest {

	@Tested
	private DmoToMessageMapperService mapperService;

	@Test
	public void testMapSingle() {
		final AtomChangeEventDmo eventDmo = TestData.atomChangeEventDmo();
		final AtomChangeEvent event = mapperService.map(eventDmo);
		assertEquals(eventDmo.getId(), event.getId());
		assertEquals(eventDmo.getAtomId(), event.getAtomId());
		assertEquals(eventDmo.getAtomType(), event.getAtomType());
	}

	@Test
	public void testMapMultiple() {
		final AtomChangeEventDmo eventDmo1 = TestData.atomChangeEventDmo();
		final AtomChangeEventDmo eventDmo2 = TestData.atomChangeEventDmo();
		final AtomChangeEvent event1 = TestData.atomChangeEvent();
		final AtomChangeEvent event2 = TestData.atomChangeEvent();
		final List<AtomChangeEventDmo> eventDmos = Arrays.asList(eventDmo1, eventDmo2);
		final List<AtomChangeEvent> expectedEvents = Arrays.asList(event1, event2);
		new StrictExpectations(mapperService) {{
			mapperService.map(eventDmo1);
			times = 1;
			result = event1;
			mapperService.map(eventDmo2);
			times = 1;
			result = event2;
		}};
		final List<AtomChangeEvent> actualEvents = mapperService.map(eventDmos);
		assertEquals(expectedEvents, actualEvents);
	}
}
