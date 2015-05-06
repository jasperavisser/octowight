package nl.haploid.octowight.registry.data;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ResourceRootTest {

	@Test
	public void testKey() {
		final ResourceRoot resourceRoot = new ResourceRoot();
		resourceRoot.setResourceType("cosgrove");
		resourceRoot.setAtomId(69l);
		resourceRoot.setAtomOrigin("california");
		resourceRoot.setAtomType("campbell");
		final String expectedKey = "california:campbell/69->cosgrove";
		final String actualKey = resourceRoot.key();
		assertEquals(expectedKey, actualKey);
	}
}
