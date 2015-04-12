package nl.haploid.resource.detector.service;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ResourceDescriptorTest {

	@Test
	public void testGetKey() {
		final ResourceDescriptor descriptor = new ResourceDescriptor();
		descriptor.setResourceType("cosgrove");
		descriptor.setAtomId(69l);
		descriptor.setAtomLocus("california");
		descriptor.setAtomType("campbell");
		final String expectedKey = "california:campbell/69->cosgrove";
		final String actualKey = descriptor.getKey();
		assertEquals(expectedKey, actualKey);
	}
}
