package nl.haploid.octowight.registry.repository;

import nl.haploid.octowight.registry.AbstractIT;
import nl.haploid.octowight.registry.TestData;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

public class ResourceModelDmoRepositoryIT extends AbstractIT {

	@Autowired
	private ResourceModelDmoRepository resourceModelDmoRepository;

	@Test
	public void testFindOne() {
		// TODO: move to TestData
		final ResourceModelDmo expectedResourceModelDmo = new ResourceModelDmo();
		final ResourceModelId resourceModelId = new ResourceModelId();
		resourceModelId.setResourceId(TestData.nextLong());
		resourceModelId.setResourceType(TestData.nextString());
		expectedResourceModelDmo.setId(resourceModelId);
		expectedResourceModelDmo.setBody(TestData.nextString());
		resourceModelDmoRepository.save(expectedResourceModelDmo);
		final ResourceModelDmo actualResourceModelDmo = resourceModelDmoRepository.findOne(resourceModelId);
		assertEquals(expectedResourceModelDmo.getBody(), actualResourceModelDmo.getBody());
	}
}
