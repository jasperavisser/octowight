package nl.haploid.resource.detector.service;

import mockit.Injectable;
import mockit.Mocked;
import mockit.StrictExpectations;
import mockit.Tested;
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
}
