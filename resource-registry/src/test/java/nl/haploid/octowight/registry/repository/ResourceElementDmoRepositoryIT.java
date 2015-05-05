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
	private ResourceElementDmoRepository repository;

	@Test
	public void testFindByAtomIdAndAtomTypeAndAtomLocus() {
		final ResourceElementDmo dmo = TestData.resourceElementDmo();
		final ResourceElementDmo expectedDmo = repository.save(dmo);
		final ResourceElementDmo actualDmo = repository
				.findByAtomIdAndAtomTypeAndAtomLocus(expectedDmo.getAtomId(), expectedDmo.getAtomType(), expectedDmo.getAtomLocus());
		assertEquals(expectedDmo, actualDmo);
	}

	@Test
	public void testFindByAtomIdInAndAtomTypeAndAtomLocus() {
		final ResourceElementDmo dmo = TestData.resourceElementDmo();
		final ResourceElementDmo expectedDmo = repository.save(dmo);
		final List<ResourceElementDmo> actualDmos = repository
				.findByAtomIdInAndAtomTypeAndAtomLocus(Collections.singletonList(expectedDmo.getAtomId()), expectedDmo.getAtomType(), expectedDmo.getAtomLocus());
		assertEquals(1, actualDmos.size());
		assertEquals(expectedDmo, actualDmos.get(0));
	}

	@Test
	public void testDeleteByResourceTypeAndResourceId() {
		final ResourceElementDmo dmo = TestData.resourceElementDmo();
		final ResourceElementDmo expectedDmo = repository.save(dmo);
		final ResourceElementDmo actualDmoBeforeDelete = repository.findOne(expectedDmo.getId());
		assertNotNull(actualDmoBeforeDelete);
		repository.deleteByResourceTypeAndResourceId(expectedDmo.getResourceType(), expectedDmo.getResourceId());
		final ResourceElementDmo actualDmoAfterDelete = repository.findOne(expectedDmo.getId());
		assertNull(actualDmoAfterDelete);
	}
}
