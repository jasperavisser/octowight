package nl.haploid.octowight.sample.service;

import mockit.Tested;
import nl.haploid.octowight.AtomChangeEvent;
import nl.haploid.octowight.sample.TestData;
import nl.haploid.octowight.sample.repository.AtomChangeEventDmo;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AtomChangeEventFactoryTest {

	@Tested
	private AtomChangeEventFactory eventFactory;

	@Test
	public void testFromAtomChangeEventDmo() {
		final AtomChangeEventDmo eventDmo = TestData.atomChangeEventDmo();
		final AtomChangeEvent event = eventFactory.fromAtomChangeEventDmo(eventDmo);
		assertEquals(eventDmo.getId(), event.getId());
		assertEquals(eventDmo.getAtomId(), event.getAtomId());
		assertEquals(eventDmo.getAtomType(), event.getAtomType());
	}

	@Test
	public void testFromAtomChangeEventDmos() {
		final AtomChangeEventDmo eventDmo1 = TestData.atomChangeEventDmo();
		final AtomChangeEventDmo eventDmo2 = TestData.atomChangeEventDmo();
		final List<AtomChangeEventDmo> eventDmos = Arrays.asList(eventDmo1, eventDmo2);
		final List<AtomChangeEvent> actualEvents = eventFactory.fromAtomChangeEventDmos(eventDmos);
		assertEquals(2, actualEvents.size());
	}
}
