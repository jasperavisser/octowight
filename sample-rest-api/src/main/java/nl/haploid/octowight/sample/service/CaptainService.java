package nl.haploid.octowight.sample.service;

import nl.haploid.octowight.registry.data.Resource;
import nl.haploid.octowight.registry.data.ResourceFactory;
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

    @Autowired
    private PersonDmoRepository personDmoRepository;

    @Autowired
    private ResourceDmoRepository resourceDmoRepository;

    @Autowired
    private ResourceElementDmoRepository resourceElementDmoRepository;

    @Autowired
    private CaptainFactory captainFactory;

    @Autowired
    private ResourceFactory resourceFactory;

    @Autowired
    private ResourceElementDmoFactory resourceElementDmoFactory;

    public Captain getCaptain(final long resourceId) {
        final Resource resource = getResource(resourceId);
        // TODO: wrap this in some sort of captain resource builder object
        // TODO: given a resource: get elements, save elements, build representation
        final PersonDmo personDmo = personDmoRepository.findOne(resource.getAtomId());
        if (personDmo == null) {
            throw new ResourceNotFoundException();
        }
        saveResourceElements(resource, personDmo);
        final Captain captain = captainFactory.fromPersonDmo(personDmo, resource.getResourceId());
        captain.setId(resource.getResourceId());
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
    protected Resource getResource(final long resourceId) {
        final ResourceDmo resourceDmo = resourceDmoRepository.findByResourceTypeAndResourceId(Captain.RESOURCE_TYPE, resourceId);
        if (resourceDmo == null) {
            throw new ResourceNotFoundException();
        }
        return resourceFactory.fromResourceDmo(resourceDmo);
    }

    // TODO: test
    // TODO: transactional over resourceRegistry
    protected void saveResourceElements(final Resource resource, final PersonDmo personDmo) {
        resourceElementDmoRepository.deleteByResourceTypeAndResourceId(resource.getResourceType(), resource.getResourceId());
        final ResourceElementDmo dmo = resourceElementDmoFactory.fromResourceAndAtom(resource, personDmo);
        resourceElementDmoRepository.save(dmo);
    }
}
