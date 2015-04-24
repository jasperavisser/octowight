package nl.haploid.octowight.sample.service;

import nl.haploid.octowight.registry.data.ResourceRoot;
import nl.haploid.octowight.registry.data.ResourceRootFactory;
import nl.haploid.octowight.registry.repository.*;
import nl.haploid.octowight.sample.controller.ResourceNotFoundException;
import nl.haploid.octowight.sample.data.Captain;
import nl.haploid.octowight.sample.data.CaptainFactory;
import nl.haploid.octowight.sample.repository.PersonDmo;
import nl.haploid.octowight.sample.repository.PersonDmoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CaptainService {

    /**
     * ResourceRoot: something that can be addressed by a URL and fetched (id, type, toJson)
     * Atom: something in a data store (id, type, locus)
     * ResourceRoot: mapping of a ResourceRoot to its root atom (atom_id, atom_type, atom_locus, resource_id, resource_type)
     */

    /**
     * Given: resource type + id
     *
     * Fetch resource representation + version from cache
     * (ResourceCacheDmoRepository -> ResourceCacheDmo)
     * Fetch resource version
     * If resource version == cached version {
     *      Return cached representation
     * }
     *
     * Fetch atoms for resource
     * Save resource->atoms map
     * Build resource representation
     * Save resource representation + version to cache
     * Return representation
     */

    @Autowired
    private PersonDmoRepository personDmoRepository;

    @Autowired
    private ResourceDmoRepository resourceDmoRepository;

    @Autowired
    private ResourceElementDmoRepository resourceElementDmoRepository;

    @Autowired
    private CaptainFactory captainFactory;

    @Autowired
    private ResourceRootFactory resourceRootFactory;

    @Autowired
    private ResourceElementDmoFactory resourceElementDmoFactory;

    public Captain getCaptain(final long resourceId) {
        final ResourceRoot resourceRoot = getResource(resourceId);
        // TODO: wrap this in some sort of captain resource builder object
        // TODO: given a resource: get elements, save elements, build representation
        final PersonDmo personDmo = personDmoRepository.findOne(resourceRoot.getAtomId());
        if (personDmo == null) {
            throw new ResourceNotFoundException();
        }
        saveResourceElements(resourceRoot, personDmo);
        final Captain captain = captainFactory.fromPersonDmo(personDmo, resourceRoot.getResourceId());
        captain.setId(resourceRoot.getResourceId());
        return captain;
    }

    public List<Captain> getCaptains() {
        final Map<Long, Long> atomIdToResourceId = resourceDmoRepository
                .findByResourceType(Captain.RESOURCE_TYPE).stream()
                .collect(Collectors.toMap(ResourceDmo::getAtomId, ResourceDmo::getResourceId));
        return personDmoRepository.findAll(atomIdToResourceId.keySet())
                .stream()
                .map(personDmo -> captainFactory.fromPersonDmo(personDmo, atomIdToResourceId.get(personDmo.getId())))
                .collect(Collectors.toList());
    }

    // TODO: test
    protected ResourceRoot getResource(final long resourceId) {
        final ResourceDmo resourceDmo = resourceDmoRepository.findByResourceTypeAndResourceId(Captain.RESOURCE_TYPE, resourceId);
        if (resourceDmo == null) {
            throw new ResourceNotFoundException();
        }
        return resourceRootFactory.fromResourceDmo(resourceDmo);
    }

    // TODO: test
    // TODO: transactional over resourceRegistry
    protected void saveResourceElements(final ResourceRoot resourceRoot, final PersonDmo personDmo) {
        resourceElementDmoRepository.deleteByResourceTypeAndResourceId(resourceRoot.getResourceType(), resourceRoot.getResourceId());
        final ResourceElementDmo dmo = resourceElementDmoFactory.fromResourceAndAtom(resourceRoot, personDmo);
        resourceElementDmoRepository.save(dmo);
    }
}
