package nl.haploid.octowight.repository;

import nl.haploid.octowight.AbstractIT;
import nl.haploid.octowight.TestData;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ResourceDmoRepositoryIT extends AbstractIT {

	@Autowired
	private ResourceDmoRepository repository;

	@Test
	public void findByAtomIdAndAtomTypeAndAtomLocus() {
		final ResourceDmo dmo = TestData.resourceDmo();
		final ResourceDmo expectedDmo = repository.save(dmo);
		final ResourceDmo actualDmo = repository.findByAtomIdAndAtomTypeAndAtomLocus(expectedDmo.getAtomId(), expectedDmo.getAtomType(), expectedDmo.getAtomLocus());
		assertEquals(expectedDmo, actualDmo);
	}

	@Test
	public void findByResourceType() {
		final ResourceDmo dmo1 = TestData.resourceDmo("willow");
		final ResourceDmo dmo2 = TestData.resourceDmo("oz");
		final ResourceDmo expectedDmo1 = repository.save(dmo1);
		repository.save(dmo2);
		final List<ResourceDmo> actualDmos = repository.findByResourceType("willow");
		final List<ResourceDmo> expectedDmos = Collections.singletonList(expectedDmo1);
		assertEquals(expectedDmos, actualDmos);
	}

	@Test
	public void findByResourceTypeAndResourceId() {
		final ResourceDmo dmo1 = TestData.resourceDmo("willow");
		final ResourceDmo dmo2 = TestData.resourceDmo("oz");
		final ResourceDmo expectedDmo = repository.save(dmo1);
		repository.save(dmo2);
		final ResourceDmo actualDmo = repository.findByResourceTypeAndResourceId("willow", expectedDmo.getResourceId());
		assertEquals(expectedDmo, actualDmo);
	}
}
