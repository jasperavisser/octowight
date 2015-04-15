package nl.haploid.octowight.data;

import nl.haploid.octowight.AtomChangeEvent;
import nl.haploid.octowight.TestData;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ResourceCoreAtomFactoryTest {

	@Test
	public void testFromAtomChangeEvent() {
		final AtomChangeEvent event = TestData.atomChangeEvent("pam");
		final String resourceType = "greer";
		final ResourceCoreAtom coreAtom = ResourceCoreAtomFactory.fromAtomChangeEvent(event, resourceType);
		assertEquals(event.getAtomId(), coreAtom.getAtomId());
		assertEquals(event.getAtomLocus(), coreAtom.getAtomLocus());
		assertEquals(event.getAtomType(), coreAtom.getAtomType());
		assertNull(coreAtom.getResourceId());
		assertEquals(resourceType, coreAtom.getResourceType());
	}
}
