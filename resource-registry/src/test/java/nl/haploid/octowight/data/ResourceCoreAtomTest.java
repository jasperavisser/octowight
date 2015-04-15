package nl.haploid.octowight.data;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ResourceCoreAtomTest {

	@Test
	public void testKey() {
		final ResourceCoreAtom coreAtom = new ResourceCoreAtom();
		coreAtom.setResourceType("cosgrove");
		coreAtom.setAtomId(69l);
		coreAtom.setAtomLocus("california");
		coreAtom.setAtomType("campbell");
		final String expectedKey = "california:campbell/69->cosgrove";
		final String actualKey = coreAtom.key();
		assertEquals(expectedKey, actualKey);
	}
}
