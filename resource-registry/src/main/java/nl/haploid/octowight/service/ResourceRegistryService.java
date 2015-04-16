package nl.haploid.octowight.service;

import nl.haploid.octowight.data.ResourceCoreAtom;
import nl.haploid.octowight.repository.ResourceCoreAtomDmo;
import nl.haploid.octowight.repository.ResourceCoreAtomDmoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceRegistryService {

	@Autowired
	private ResourceCoreAtomDmoRepository resourceRepository;

	@Autowired
	private DmoToMessageMapperService mapperService;

	public boolean isNewResource(final ResourceCoreAtom coreAtom) {
		final ResourceCoreAtomDmo dmo = resourceRepository.findByAtomIdAndAtomTypeAndAtomLocus(coreAtom.getAtomId(), coreAtom.getAtomType(), coreAtom.getAtomLocus());
		return dmo == null;
	}

	public ResourceCoreAtom saveResource(final ResourceCoreAtom coreAtom) {
		final ResourceCoreAtomDmo dmo = resourceRepository.saveAndFlush(mapperService.map(coreAtom));
		return mapperService.map(dmo);
	}
}
