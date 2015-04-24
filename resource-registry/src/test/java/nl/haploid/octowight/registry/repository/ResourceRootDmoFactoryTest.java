package nl.haploid.octowight.registry.repository;

import mockit.Tested;
import nl.haploid.octowight.registry.TestData;
import nl.haploid.octowight.registry.data.ResourceRoot;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ResourceRootDmoFactoryTest {

	@Tested
	private ResourceRootDmoFactory resourceRootDmoFactory;

	@Test
	public void testFromResource() {
		final ResourceRoot resourceRoot = TestData.resourceRoot(123l);
		final ResourceRootDmo resourceRootDmo = resourceRootDmoFactory.fromResourceRoot(resourceRoot);
		assertEquals(resourceRoot.getAtomId(), resourceRootDmo.getAtomId());
		assertEquals(resourceRoot.getAtomType(), resourceRootDmo.getAtomType());
		assertEquals(resourceRoot.getAtomLocus(), resourceRootDmo.getAtomLocus());
		assertEquals(resourceRoot.getResourceId(), resourceRootDmo.getResourceId());
		assertEquals(resourceRoot.getResourceType(), resourceRootDmo.getResourceType());
	}
}
