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
	private ResourceRootDmoRepository resourceRootDmoRepository;

	@Test
	public void findByAtomIdAndAtomTypeAndAtomLocus() {
		resourceRootDmoRepository.deleteAll();
		final ResourceRootDmo dmo = TestData.resourceRootDmo();
		final ResourceRootDmo expectedDmo = resourceRootDmoRepository.save(dmo);
		final ResourceRootDmo actualDmo = resourceRootDmoRepository
				.findByResourceTypeAndAtomIdAndAtomTypeAndAtomLocus(expectedDmo.getResourceType(),
						expectedDmo.getAtomId(), expectedDmo.getAtomType(), expectedDmo.getAtomLocus());
		assertEquals(expectedDmo, actualDmo);
	}

	@Test
	public void findByResourceType() {
		resourceRootDmoRepository.deleteAll();
		final ResourceRootDmo dmo1 = TestData.resourceRootDmo("willow");
		final ResourceRootDmo dmo2 = TestData.resourceRootDmo("oz");
		final ResourceRootDmo expectedDmo1 = resourceRootDmoRepository.save(dmo1);
		resourceRootDmoRepository.save(dmo2);
		final List<ResourceRootDmo> actualDmos = resourceRootDmoRepository.findByResourceType("willow");
		final List<ResourceRootDmo> expectedDmos = Collections.singletonList(expectedDmo1);
		assertEquals(expectedDmos, actualDmos);
	}

	@Test
	public void findByResourceTypeAndResourceId() {
		resourceRootDmoRepository.deleteAll();
		final ResourceRootDmo dmo1 = TestData.resourceRootDmo("willow");
		final ResourceRootDmo dmo2 = TestData.resourceRootDmo("oz");
		final ResourceRootDmo expectedDmo = resourceRootDmoRepository.save(dmo1);
		resourceRootDmoRepository.save(dmo2);
		final ResourceRootDmo actualDmo = resourceRootDmoRepository.findByResourceTypeAndResourceId("willow", expectedDmo.getResourceId());
		assertEquals(expectedDmo, actualDmo);
	}
}
