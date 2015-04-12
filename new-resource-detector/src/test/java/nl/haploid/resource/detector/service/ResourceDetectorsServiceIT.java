package nl.haploid.resource.detector.service;

import mockit.Deencapsulation;
import mockit.StrictExpectations;
import nl.haploid.event.RowChangeEvent;
import nl.haploid.resource.detector.AbstractIT;
import nl.haploid.resource.detector.TestData;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Arrays;
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
		Deencapsulation.setField(service, "detectors", Arrays.asList(mockDetector));
	}

	@Test
	public void testGetDetectorsForTable() {
		final List<MockResourceDetector> expectedDetectors = Arrays.asList(mockDetector);
		final String tableName = "kinsey";
		new StrictExpectations(mockDetector) {{
			mockDetector.getTableNames();
			times = 1;
			result = Arrays.asList("crane", tableName);
		}};
		final List<ResourceDetector> actualDetectors = service.getDetectorsForTable(tableName);
		assertEquals(expectedDetectors, actualDetectors);
	}

	@Test
	public void testDetectResources() {
		final String tableName = "harris";
		final RowChangeEvent event1 = TestData.rowChangeEvent(tableName);
		final RowChangeEvent event2 = TestData.rowChangeEvent("calvet");
		final List<RowChangeEvent> events = Arrays.asList(event1, event2);
		final List<ResourceDescriptor> expectedDescriptors = Arrays.asList(TestData.resourceDescriptor(96l));
		new StrictExpectations(mockDetector) {{
			mockDetector.getTableNames();
			times = 1;
			result = Arrays.asList("holloway", tableName);
			mockDetector.detect(events);
			times = 1;
			result = expectedDescriptors;
		}};
		final List<ResourceDescriptor> actualDescriptors = service.detectResources(tableName, events);
		assertEquals(expectedDescriptors, actualDescriptors);
	}
}
