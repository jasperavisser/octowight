package nl.haploid.octowight.sample.service;

import nl.haploid.octowight.registry.repository.ResourceDmo;
import nl.haploid.octowight.registry.repository.ResourceDmoRepository;
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
	private CaptainFactory captainFactory;

	public Captain getCaptain(final long resourceId) {
		final ResourceDmo resourceDmo = resourceDmoRepository.findByResourceTypeAndResourceId(Captain.RESOURCE_TYPE, resourceId);
		// TODO: handle non-existent (404)
		final PersonDmo personDmo = personDmoRepository.findOne(resourceDmo.getAtomId());
		final Captain captain = captainFactory.fromPersonDmo(personDmo, resourceId);
		captain.setId(resourceDmo.getResourceId());
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
}
