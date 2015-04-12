package nl.haploid.resource.detector.service;

import mockit.Injectable;
import mockit.Mocked;
import mockit.StrictExpectations;
import mockit.Tested;
import nl.haploid.event.AtomChangeEvent;
import nl.haploid.resource.detector.TestData;
import nl.haploid.resource.detector.detector.ResourceDetector;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@Ignore
public class ResourceDetectorsServiceTest {

	@Tested
	private ResourceDetectorsService service;

	@Injectable
	private List<ResourceDetector> mockDetectors;

	@Test
	public void testGetDetectorsForAtomType(final @Mocked ResourceDetector mockDetector1,
											final @Mocked ResourceDetector mockDetector2) {
		final List<ResourceDetector> expectedDetectors = Arrays.asList(mockDetector1);
		final String atomType = "sterling";
		new StrictExpectations(service) {{
			service.getDetectors();
			times = 1;
			result = Arrays.asList(mockDetector1, mockDetector2);
			mockDetector1.getAtomTypes();
			times = 1;
			result = Arrays.asList(atomType);
			mockDetector2.getAtomTypes();
			times = 1;
			result = Arrays.asList("cooper");
		}};
		final List<ResourceDetector> actualDetectors = service.getDetectorsForAtomType(atomType);
		assertEquals(expectedDetectors, actualDetectors);
	}

	@Test
	public void testDetectResources(final @Mocked ResourceDetector mockDetector) {
		final AtomChangeEvent event1 = TestData.atomChangeEvent("draper");
		final AtomChangeEvent event2 = TestData.atomChangeEvent("pryce");
		final List<AtomChangeEvent> events = Arrays.asList(event1, event2);
		final List<ResourceDescriptor> expectedDescriptors = Arrays.asList(TestData.resourceDescriptor(null));
		new StrictExpectations(service) {{
			service.getDetectorsForAtomType("draper");
			times = 1;
			result = Arrays.asList(mockDetector);
			mockDetector.detect(events);
			times = 1;
			result = expectedDescriptors;
		}};
		final List<ResourceDescriptor> actualDescriptors = service.detectResources("draper", events);
		assertEquals(expectedDescriptors, actualDescriptors);
	}
}
