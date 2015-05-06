package nl.haploid.octowight.service;

import mockit.Injectable;
import mockit.Mocked;
import mockit.StrictExpectations;
import mockit.Tested;
import nl.haploid.octowight.AtomChangeEvent;
import nl.haploid.octowight.AtomGroup;
import nl.haploid.octowight.TestData;
import nl.haploid.octowight.detector.ResourceDetector;
import nl.haploid.octowight.registry.data.ResourceRoot;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ResourceDetectorsServiceTest {

	@Tested
	private ResourceDetectorsService service;

	@Injectable
	private List<ResourceDetector> mockDetectors;

	@Test
	public void testGetDetectorsForAtomType(final @Mocked ResourceDetector mockDetector1,
	                                        final @Mocked ResourceDetector mockDetector2) {
		final List<ResourceDetector> expectedDetectors = Collections.singletonList(mockDetector1);
		final String atomType = "sterling";
		new StrictExpectations(service) {{
			service.getDetectors();
			times = 1;
			result = Arrays.asList(mockDetector1, mockDetector2);
			mockDetector1.getAtomTypes();
			times = 1;
			result = Collections.singletonList(atomType);
			mockDetector2.getAtomTypes();
			times = 1;
			result = Collections.singletonList("cooper");
		}};
		final List<ResourceDetector> actualDetectors = service.getDetectorsForAtomType(atomType);
		assertEquals(expectedDetectors, actualDetectors);
	}

	@Test
	public void testDetectResources(final @Mocked ResourceDetector mockDetector) {
		final AtomChangeEvent event1 = TestData.atomChangeEvent("draper");
		final AtomChangeEvent event2 = TestData.atomChangeEvent("pryce");
		final AtomGroup atomGroup = new AtomGroup();
		atomGroup.setAtomOrigin(event1.getAtomOrigin());
		atomGroup.setAtomType(event1.getAtomType());
		final List<AtomChangeEvent> events = Arrays.asList(event1, event2);
		final List<ResourceRoot> expectedResourceRoots = Collections.singletonList(TestData.resourceRoot(null));
		new StrictExpectations(service) {{
			service.getDetectorsForAtomType("draper");
			times = 1;
			result = Collections.singletonList(mockDetector);
			mockDetector.detect(events);
			times = 1;
			result = expectedResourceRoots;
		}};
		final List<ResourceRoot> actualResourceRoots = service.detectResources(atomGroup, events);
		assertEquals(expectedResourceRoots, actualResourceRoots);
	}
}
