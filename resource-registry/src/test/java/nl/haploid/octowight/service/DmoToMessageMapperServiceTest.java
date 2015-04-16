package nl.haploid.octowight.service;

import mockit.Tested;
import nl.haploid.octowight.TestData;
import nl.haploid.octowight.data.ResourceCoreAtom;
import nl.haploid.octowight.repository.ResourceCoreAtomDmo;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DmoToMessageMapperServiceTest {

	@Tested
	private DmoToMessageMapperService mapperService;

	@Test
	public void testMapResourceCoreAtomDmo() {
		final ResourceCoreAtomDmo coreAtomDmo = TestData.resourceCoreAtomDmo();
		final ResourceCoreAtom coreAtom = mapperService.map(coreAtomDmo);
		assertEquals(coreAtomDmo.getAtomId(), coreAtom.getAtomId());
		assertEquals(coreAtomDmo.getAtomType(), coreAtom.getAtomType());
		assertEquals(coreAtomDmo.getAtomLocus(), coreAtom.getAtomLocus());
		assertEquals(coreAtomDmo.getResourceId(), coreAtom.getResourceId());
		assertEquals(coreAtomDmo.getResourceType(), coreAtom.getResourceType());
	}

	@Test
	public void testMapResourceCoreAtom() {
		final ResourceCoreAtom coreAtom = TestData.resourceCoreAtom(123l);
		final ResourceCoreAtomDmo coreAtomDmo = mapperService.map(coreAtom);
		assertEquals(coreAtom.getAtomId(), coreAtomDmo.getAtomId());
		assertEquals(coreAtom.getAtomType(), coreAtomDmo.getAtomType());
		assertEquals(coreAtom.getAtomLocus(), coreAtomDmo.getAtomLocus());
		assertEquals(coreAtom.getResourceId(), coreAtomDmo.getResourceId());
		assertEquals(coreAtom.getResourceType(), coreAtomDmo.getResourceType());
	}
}
