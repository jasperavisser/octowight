package nl.haploid.octowight.registry.repository;

import nl.haploid.octowight.registry.AbstractIT;
import nl.haploid.octowight.registry.TestData;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class ResourceElementDmoRepositoryIT extends AbstractIT {

	@Autowired
	private ResourceElementDmoRepository resourceElementDmoRepository;

	@Test
	public void testFindByAtomIdAndAtomTypeAndAtomOrigin() {
		final ResourceElementDmo dmo = TestData.resourceElementDmo();
		final ResourceElementDmo expectedDmo = resourceElementDmoRepository.save(dmo);
		final ResourceElementDmo actualDmo = resourceElementDmoRepository
				.findByAtomIdAndAtomTypeAndAtomOrigin(expectedDmo.getAtomId(), expectedDmo.getAtomType(), expectedDmo.getAtomOrigin());
		assertEquals(expectedDmo, actualDmo);
	}

	@Test
	public void testFindByAtomIdInAndAtomTypeAndAtomOrigin() {
		final ResourceElementDmo dmo = TestData.resourceElementDmo();
		final ResourceElementDmo expectedDmo = resourceElementDmoRepository.save(dmo);
		final List<ResourceElementDmo> actualDmos = resourceElementDmoRepository
				.findByAtomIdInAndAtomTypeAndAtomOrigin(Collections.singletonList(expectedDmo.getAtomId()), expectedDmo.getAtomType(), expectedDmo.getAtomOrigin());
		assertEquals(1, actualDmos.size());
		assertEquals(expectedDmo, actualDmos.get(0));
	}

	@Test
	public void testDeleteByResourceTypeAndResourceId() {
		final ResourceElementDmo dmo = TestData.resourceElementDmo();
		final ResourceElementDmo expectedDmo = resourceElementDmoRepository.save(dmo);
		final ResourceElementDmo actualDmoBeforeDelete = resourceElementDmoRepository.findOne(expectedDmo.getId());
		assertNotNull(actualDmoBeforeDelete);
		resourceElementDmoRepository.deleteByResourceTypeAndResourceId(expectedDmo.getResourceType(), expectedDmo.getResourceId());
		final ResourceElementDmo actualDmoAfterDelete = resourceElementDmoRepository.findOne(expectedDmo.getId());
		assertNull(actualDmoAfterDelete);
	}
}
