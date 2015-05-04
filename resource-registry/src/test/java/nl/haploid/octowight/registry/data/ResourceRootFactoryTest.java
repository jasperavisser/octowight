package nl.haploid.octowight.registry.data;

import mockit.Tested;
import nl.haploid.octowight.AtomChangeEvent;
import nl.haploid.octowight.registry.TestData;
import nl.haploid.octowight.registry.repository.ResourceRootDmo;
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
		final ResourceRootDmo resourceRootDmo = TestData.resourceRootDmo();
		final ResourceRoot resourceRoot = resourceRootFactory.fromResourceDmo(resourceRootDmo);
		assertEquals(resourceRootDmo.getAtomId(), resourceRoot.getAtomId());
		assertEquals(resourceRootDmo.getAtomType(), resourceRoot.getAtomType());
		assertEquals(resourceRootDmo.getAtomLocus(), resourceRoot.getAtomLocus());
		assertEquals(resourceRootDmo.getResourceId(), resourceRoot.getResourceId());
		assertEquals(resourceRootDmo.getResourceType(), resourceRoot.getResourceType());
	}
}
