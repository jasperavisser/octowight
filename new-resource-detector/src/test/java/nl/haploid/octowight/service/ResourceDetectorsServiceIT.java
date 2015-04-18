package nl.haploid.octowight.service;

import mockit.Deencapsulation;
import mockit.StrictExpectations;
import nl.haploid.octowight.AbstractIT;
import nl.haploid.octowight.AtomChangeEvent;
import nl.haploid.octowight.TestData;
import nl.haploid.octowight.detector.MockResourceDetector;
import nl.haploid.octowight.detector.ResourceDetector;
import nl.haploid.octowight.registry.data.Resource;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

@DirtiesContext
public class ResourceDetectorsServiceIT extends AbstractIT {

	@Autowired
	private ResourceDetectorsService service;

	@Autowired
	private MockResourceDetector mockDetector;

	@Before
	public void setUp() throws Exception {
		Deencapsulation.setField(service, "detectors", Collections.singletonList(mockDetector));
	}

	@Test
	public void testGetDetectorsForAtomType() {
		final List<MockResourceDetector> expectedDetectors = Collections.singletonList(mockDetector);
		final String atomType = "kinsey";
		new StrictExpectations(mockDetector) {{
			mockDetector.getAtomTypes();
			times = 1;
			result = Arrays.asList("crane", atomType);
		}};
		final List<ResourceDetector> actualDetectors = service.getDetectorsForAtomType(atomType);
		assertEquals(expectedDetectors, actualDetectors);
	}

	@Test
	public void testDetectResources() {
		final String atomType = "harris";
		final AtomChangeEvent event1 = TestData.atomChangeEvent(atomType);
		final AtomChangeEvent event2 = TestData.atomChangeEvent("calvet");
		final List<AtomChangeEvent> events = Arrays.asList(event1, event2);
		final List<Resource> expectedResources = Collections.singletonList(TestData.resource(96l));
		new StrictExpectations(mockDetector) {{
			mockDetector.getAtomTypes();
			times = 1;
			result = Arrays.asList("holloway", atomType);
			mockDetector.detect(events);
			times = 1;
			result = expectedResources;
		}};
		final List<Resource> actualResources = service.detectResources(atomType, events);
		assertEquals(expectedResources, actualResources);
	}
}
