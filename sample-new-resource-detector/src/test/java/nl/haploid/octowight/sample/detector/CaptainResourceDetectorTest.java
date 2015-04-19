package nl.haploid.octowight.sample.detector;

import mockit.Injectable;
import mockit.Mocked;
import mockit.StrictExpectations;
import mockit.Tested;
import nl.haploid.octowight.AtomChangeEvent;
import nl.haploid.octowight.registry.data.Resource;
import nl.haploid.octowight.registry.data.ResourceFactory;
import nl.haploid.octowight.sample.TestData;
import nl.haploid.octowight.sample.repository.PersonDmo;
import nl.haploid.octowight.sample.repository.PersonDmoRepository;
import nl.haploid.octowight.sample.repository.RoleDmo;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CaptainResourceDetectorTest {

	@Tested
	private CaptainResourceDetector detector;

	@Injectable
	private PersonDmoRepository personDmoRepository;

	@Injectable
	private ResourceFactory resourceFactory;

	@Test
	public void testGetAtomTypes() throws Exception {
		assertEquals(1, detector.getAtomTypes().size());
	}

	@Test
	public void testDetect() throws Exception {
		final AtomChangeEvent event1 = TestData.atomChangeEvent();
		final AtomChangeEvent event2 = TestData.atomChangeEvent();
		final AtomChangeEvent event3 = TestData.atomChangeEvent();
		final List<AtomChangeEvent> events = Arrays.asList(event1, event2, event3);
		final Long id1 = event1.getAtomId();
		final Long id2 = event2.getAtomId();
		final PersonDmo personDmo1 = TestData.personDmo(id1);
		final PersonDmo personDmo2 = TestData.personDmo(id2);
		final HashMap<Long, PersonDmo> personsById = new HashMap<Long, PersonDmo>() {{
			put(id1, personDmo1);
			put(id2, personDmo2);
		}};
		final Resource resource = TestData.resource(id2);
		final List<Resource> expectedResources = Collections.singletonList(resource);
		new StrictExpectations(detector) {{
			detector.getPersonsById(events);
			times = 1;
			result = personsById;
			detector.isCaptain(personDmo1);
			times = 1;
			result = false;
			detector.isCaptain(personDmo2);
			times = 1;
			result = true;
			resourceFactory.fromAtomChangeEvent(event2, CaptainResourceDetector.RESOURCE_TYPE);
			times = 1;
			result = resource;
		}};
		final List<Resource> actualResources = detector.detect(events);
		assertEquals(expectedResources, actualResources);
	}

	@Test
	public void testGetPersonsById() throws Exception {
		final AtomChangeEvent event1 = TestData.atomChangeEvent();
		final AtomChangeEvent event2 = TestData.atomChangeEvent();
		final List<AtomChangeEvent> events = Arrays.asList(event1, event2);
		final Long id1 = event1.getAtomId();
		final Long id2 = event2.getAtomId();
		final PersonDmo personDmo1 = TestData.personDmo(id1);
		final PersonDmo personDmo2 = TestData.personDmo(id2);
		new StrictExpectations() {{
			personDmoRepository.findAll(Arrays.asList(id1, id2));
			times = 1;
			result = Arrays.asList(personDmo1, personDmo2);
		}};
		final Map<Long, PersonDmo> personsById = detector.getPersonsById(events);
		assertEquals(personDmo1, personsById.get(id1));
		assertEquals(personDmo2, personsById.get(id2));
	}

	@Test
	public void testIsCaptain(final @Mocked PersonDmo personDmo) {
		final RoleDmo roleDmo1 = TestData.roleDmo(personDmo, "harpooner");
		final RoleDmo roleDmo2 = TestData.roleDmo(personDmo, CaptainResourceDetector.ROLE_TYPE);
		new StrictExpectations() {{
			personDmo.getRoles();
			times = 1;
			result = Arrays.asList(roleDmo1, roleDmo2);
		}};
		assertTrue(detector.isCaptain(personDmo));
	}
}
