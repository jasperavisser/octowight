package nl.haploid.octowight.registry.repository;

import mockit.Tested;
import nl.haploid.octowight.registry.TestData;
import nl.haploid.octowight.registry.data.ResourceRoot;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ResourceRootDmoFactoryTest {

	@Tested
	private ResourceDmoFactory resourceDmoFactory;

	@Test
	public void testFromResource() {
		final ResourceRoot resourceRoot = TestData.resourceRoot(123l);
		final ResourceDmo resourceDmo = resourceDmoFactory.fromResource(resourceRoot);
		assertEquals(resourceRoot.getAtomId(), resourceDmo.getAtomId());
		assertEquals(resourceRoot.getAtomType(), resourceDmo.getAtomType());
		assertEquals(resourceRoot.getAtomLocus(), resourceDmo.getAtomLocus());
		assertEquals(resourceRoot.getResourceId(), resourceDmo.getResourceId());
		assertEquals(resourceRoot.getResourceType(), resourceDmo.getResourceType());
	}
}
