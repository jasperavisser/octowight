package nl.haploid.resource.detector.service;

import mockit.Injectable;
import mockit.Mocked;
import mockit.StrictExpectations;
import mockit.Tested;
import nl.haploid.event.RowChangeEvent;
import nl.haploid.resource.detector.TestData;
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
	public void testGetDetectorsForTable(final @Mocked ResourceDetector mockDetector1,
										 final @Mocked ResourceDetector mockDetector2) {
		final List<ResourceDetector> expectedDetectors = Arrays.asList(mockDetector1);
		final String tableName = "sterling";
		new StrictExpectations(service) {{
			service.getDetectors();
			times = 1;
			result = Arrays.asList(mockDetector1, mockDetector2);
			mockDetector1.getTableNames();
			times = 1;
			result = Arrays.asList(tableName);
			mockDetector2.getTableNames();
			times = 1;
			result = Arrays.asList("cooper");
		}};
		final List<ResourceDetector> actualDetectors = service.getDetectorsForTable(tableName);
		assertEquals(expectedDetectors, actualDetectors);
	}

	@Test
	public void testDetectResources(final @Mocked ResourceDetector mockDetector) {
		final RowChangeEvent event1 = TestData.rowChangeEvent("draper");
		final RowChangeEvent event2 = TestData.rowChangeEvent("pryce");
		final List<RowChangeEvent> events = Arrays.asList(event1, event2);
		final List<ResourceDescriptor> expectedDescriptors = Arrays.asList(TestData.resourceDescriptor(null));
		new StrictExpectations(service) {{
			service.getDetectorsForTable("draper");
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
