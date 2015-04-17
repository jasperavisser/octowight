package nl.haploid.octowight.registry.repository;

import mockit.Tested;
import nl.haploid.octowight.registry.TestData;
import nl.haploid.octowight.registry.data.Resource;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ResourceDmoFactoryTest {

	@Tested
	private ResourceDmoFactory resourceDmoFactory;

	@Test
	public void testFromResource() {
		final Resource resource = TestData.resource(123l);
		final ResourceDmo resourceDmo = resourceDmoFactory.fromResource(resource);
		assertEquals(resource.getAtomId(), resourceDmo.getAtomId());
		assertEquals(resource.getAtomType(), resourceDmo.getAtomType());
		assertEquals(resource.getAtomLocus(), resourceDmo.getAtomLocus());
		assertEquals(resource.getResourceId(), resourceDmo.getResourceId());
		assertEquals(resource.getResourceType(), resourceDmo.getResourceType());
	}
}
