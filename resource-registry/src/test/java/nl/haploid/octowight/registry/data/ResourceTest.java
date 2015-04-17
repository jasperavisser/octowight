package nl.haploid.octowight.registry.data;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ResourceTest {

	@Test
	public void testKey() {
		final Resource resource = new Resource();
		resource.setResourceType("cosgrove");
		resource.setAtomId(69l);
		resource.setAtomLocus("california");
		resource.setAtomType("campbell");
		final String expectedKey = "california:campbell/69->cosgrove";
		final String actualKey = resource.key();
		assertEquals(expectedKey, actualKey);
	}
}
