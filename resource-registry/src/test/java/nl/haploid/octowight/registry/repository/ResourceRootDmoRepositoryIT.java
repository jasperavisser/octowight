package nl.haploid.octowight.registry.repository;

import nl.haploid.octowight.registry.AbstractIT;
import nl.haploid.octowight.registry.TestData;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ResourceRootDmoRepositoryIT extends AbstractIT {

	@Autowired
	private ResourceRootDmoRepository repository;

	@Test
	public void findByAtomIdAndAtomTypeAndAtomLocus() {
		final ResourceRootDmo dmo = TestData.resourceRootDmo();
		final ResourceRootDmo expectedDmo = repository.save(dmo);
		final ResourceRootDmo actualDmo = repository
				.findByResourceTypeAndAtomIdAndAtomTypeAndAtomLocus(expectedDmo.getResourceType(),
						expectedDmo.getAtomId(), expectedDmo.getAtomType(), expectedDmo.getAtomLocus());
		assertEquals(expectedDmo, actualDmo);
	}

	@Test
	public void findByResourceType() {
		final ResourceRootDmo dmo1 = TestData.resourceRootDmo("willow");
		final ResourceRootDmo dmo2 = TestData.resourceRootDmo("oz");
		final ResourceRootDmo expectedDmo1 = repository.save(dmo1);
		repository.save(dmo2);
		final List<ResourceRootDmo> actualDmos = repository.findByResourceType("willow");
		final List<ResourceRootDmo> expectedDmos = Collections.singletonList(expectedDmo1);
		assertEquals(expectedDmos, actualDmos);
	}

	@Test
	public void findByResourceTypeAndResourceId() {
		final ResourceRootDmo dmo1 = TestData.resourceRootDmo("willow");
		final ResourceRootDmo dmo2 = TestData.resourceRootDmo("oz");
		final ResourceRootDmo expectedDmo = repository.save(dmo1);
		repository.save(dmo2);
		final ResourceRootDmo actualDmo = repository.findByResourceTypeAndResourceId("willow", expectedDmo.getResourceId());
		assertEquals(expectedDmo, actualDmo);
	}
}
