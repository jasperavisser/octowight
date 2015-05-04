package nl.haploid.octowight.registry.service;

import nl.haploid.octowight.AtomChangeEvent;
import nl.haploid.octowight.AtomGroup;
import nl.haploid.octowight.registry.data.ResourceRoot;
import nl.haploid.octowight.registry.data.ResourceRootFactory;
import nl.haploid.octowight.registry.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ResourceRegistryService {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private ResourceRootDmoRepository resourceRootDmoRepository;

	@Autowired
	private ResourceElementDmoRepository resourceElementDmoRepository;

	@Autowired
	private ResourceRootFactory resourceRootFactory;

	@Autowired
	private ResourceRootDmoFactory resourceRootDmoFactory;

	@Autowired
	private VersionDmoRepository versionDmoRepository;

	public boolean isNewResource(final ResourceRoot resourceRoot) {
		final ResourceRootDmo dmo = resourceRootDmoRepository
				.findByResourceTypeAndAtomIdAndAtomTypeAndAtomLocus(resourceRoot.getResourceType(),
						resourceRoot.getAtomId(), resourceRoot.getAtomType(), resourceRoot.getAtomLocus());
		return dmo == null;
	}

	public ResourceRoot saveResource(final ResourceRoot resourceRoot) {
		final ResourceRootDmo resourceRootDmo = resourceRootDmoFactory.fromResourceRoot(resourceRoot);
		final ResourceRootDmo dmo = resourceRootDmoRepository.saveAndFlush(resourceRootDmo);
		log.debug(String.format("Saved resource: %s/%d", dmo.getResourceType(), dmo.getResourceId()));
		return resourceRootFactory.fromResourceDmo(dmo);
	}

	// TODO: test
	public List<ResourceRoot> markResourcesDirty(final AtomGroup atomGroup, final List<AtomChangeEvent> atomChangeEvents) {
		final List<Long> atomIds = atomChangeEvents.stream()
				.map(AtomChangeEvent::getAtomId)
				.collect(Collectors.toList());
		final List<ResourceElementDmo> resourceElementDmos = resourceElementDmoRepository
				.findByAtomIdInAndAtomTypeAndAtomLocus(atomIds, atomGroup.getAtomType(), atomGroup.getAtomLocus());
		return resourceElementDmos.stream()
				.map(this::getResourceRootDmo)
				.filter(Objects::nonNull)
				.map(this::markResourceDirty)
				.collect(Collectors.toList());
	}

	private ResourceRootDmo getResourceRootDmo(final ResourceElementDmo resourceElementDmo) {
		return resourceRootDmoRepository.findByResourceTypeAndResourceId(resourceElementDmo.getResourceType(), resourceElementDmo.getResourceId());
	}

	private ResourceRoot markResourceDirty(final ResourceRootDmo resourceRootDmo) {
		log.debug(String.format("Mark %s/%d as dirty", resourceRootDmo.getResourceType(), resourceRootDmo.getResourceId()));
		final VersionDmo versionDmo = versionDmoRepository.findNext();
		resourceRootDmo.setVersion(versionDmo.getVersion());
		return resourceRootFactory.fromResourceDmo(resourceRootDmoRepository.saveAndFlush(resourceRootDmo));
	}
}
