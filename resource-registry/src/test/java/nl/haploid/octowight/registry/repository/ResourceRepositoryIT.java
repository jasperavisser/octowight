package nl.haploid.octowight.registry.repository;

import nl.haploid.octowight.registry.AbstractIT;
import nl.haploid.octowight.registry.TestData;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ResourceRepositoryIT extends AbstractIT {

	@Autowired
	private ResourceDmoRepository repository;

	@Test
	@Transactional
	public void testFindAll() {
		final ResourceDmo dmo = TestData.resourceDmo();
		repository.saveAndFlush(dmo);
		final List<ResourceDmo> dmos = repository.findAll();
		assertEquals(1, dmos.size());
	}
}
