package nl.haploid.octowight.registry.data;

import mockit.Tested;
import nl.haploid.octowight.AtomChangeEvent;
import nl.haploid.octowight.registry.TestData;
import nl.haploid.octowight.registry.repository.ResourceDmo;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ResourceRootFactoryTest {

	@Tested
	private ResourceRootFactory resourceRootFactory;

	@Test
	public void testFromAtomChangeEvent() {
		final AtomChangeEvent event = TestData.atomChangeEvent("pam");
		final String resourceType = "greer";
		final ResourceRoot resourceRoot = resourceRootFactory.fromAtomChangeEvent(event, resourceType);
		assertEquals(event.getAtomId(), resourceRoot.getAtomId());
		assertEquals(event.getAtomLocus(), resourceRoot.getAtomLocus());
		assertEquals(event.getAtomType(), resourceRoot.getAtomType());
		assertNull(resourceRoot.getResourceId());
		assertEquals(resourceType, resourceRoot.getResourceType());
	}

	@Test
	public void testFromResourceDmo() {
		final ResourceDmo resourceDmo = TestData.resourceDmo();
		final ResourceRoot resourceRoot = resourceRootFactory.fromResourceDmo(resourceDmo);
		assertEquals(resourceDmo.getAtomId(), resourceRoot.getAtomId());
		assertEquals(resourceDmo.getAtomType(), resourceRoot.getAtomType());
		assertEquals(resourceDmo.getAtomLocus(), resourceRoot.getAtomLocus());
		assertEquals(resourceDmo.getResourceId(), resourceRoot.getResourceId());
		assertEquals(resourceDmo.getResourceType(), resourceRoot.getResourceType());
	}
}
