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
		final ResourceModelDmo expectedResourceModelDmo = TestData.resourceModelDmo();
		resourceModelDmoRepository.save(expectedResourceModelDmo);
		final ResourceModelDmo actualResourceModelDmo = resourceModelDmoRepository.findOne(expectedResourceModelDmo.getId());
		assertEquals(expectedResourceModelDmo.getBody(), actualResourceModelDmo.getBody());
	}
}
