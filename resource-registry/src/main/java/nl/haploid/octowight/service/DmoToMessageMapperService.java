package nl.haploid.octowight.service;

import nl.haploid.octowight.data.ResourceCoreAtom;
import nl.haploid.octowight.repository.ResourceCoreAtomDmo;
import org.springframework.stereotype.Service;

@Service
public class DmoToMessageMapperService {

	public ResourceCoreAtom map(final ResourceCoreAtomDmo coreAtomDmo) {
		final ResourceCoreAtom coreAtom = new ResourceCoreAtom();
		coreAtom.setAtomId(coreAtomDmo.getAtomId());
		coreAtom.setAtomLocus(coreAtomDmo.getAtomLocus());
		coreAtom.setAtomType(coreAtomDmo.getAtomType());
		coreAtom.setResourceId(coreAtomDmo.getResourceId());
		coreAtom.setResourceType(coreAtomDmo.getResourceType());
		return coreAtom;
	}

	public ResourceCoreAtomDmo map(final ResourceCoreAtom coreAtom) {
		final ResourceCoreAtomDmo coreAtomDmo = new ResourceCoreAtomDmo();
		coreAtomDmo.setAtomId(coreAtom.getAtomId());
		coreAtomDmo.setAtomLocus(coreAtom.getAtomLocus());
		coreAtomDmo.setAtomType(coreAtom.getAtomType());
		coreAtomDmo.setResourceId(coreAtom.getResourceId());
		coreAtomDmo.setResourceType(coreAtom.getResourceType());
		return coreAtomDmo;
	}
}
