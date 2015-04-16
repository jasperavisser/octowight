package nl.haploid.octowight.data;

import mockit.Tested;
import nl.haploid.octowight.AtomChangeEvent;
import nl.haploid.octowight.TestData;
import nl.haploid.octowight.repository.ResourceDmo;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ResourceFactoryTest {

	@Tested
	private ResourceFactory resourceFactory;

	@Test
	public void testFromAtomChangeEvent() {
		final AtomChangeEvent event = TestData.atomChangeEvent("pam");
		final String resourceType = "greer";
		final Resource resource = resourceFactory.fromAtomChangeEvent(event, resourceType);
		assertEquals(event.getAtomId(), resource.getAtomId());
		assertEquals(event.getAtomLocus(), resource.getAtomLocus());
		assertEquals(event.getAtomType(), resource.getAtomType());
		assertNull(resource.getResourceId());
		assertEquals(resourceType, resource.getResourceType());
	}

	@Test
	public void testFromResourceDmo() {
		final ResourceDmo resourceDmo = TestData.resourceDmo();
		final Resource resource = resourceFactory.fromResourceDmo(resourceDmo);
		assertEquals(resourceDmo.getAtomId(), resource.getAtomId());
		assertEquals(resourceDmo.getAtomType(), resource.getAtomType());
		assertEquals(resourceDmo.getAtomLocus(), resource.getAtomLocus());
		assertEquals(resourceDmo.getResourceId(), resource.getResourceId());
		assertEquals(resourceDmo.getResourceType(), resource.getResourceType());
	}
}
