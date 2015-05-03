package nl.haploid.octowight.sample.data;

import nl.haploid.octowight.registry.data.ResourceRoot;
import nl.haploid.octowight.sample.repository.PersonDmo;
import nl.haploid.octowight.sample.repository.PersonDmoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

// TODO: test
@Component
public class CaptainResourceFactory implements ResourceFactory<CaptainResource> {

	@Autowired
	private PersonDmoRepository personDmoRepository;

	@Override
	@Transactional(value = "sampleTransactionManager", readOnly = true)
	public CaptainResource fromResourceRoot(final ResourceRoot resourceRoot) {
		final PersonDmo personDmo = personDmoRepository.findOne(resourceRoot.getAtomId());
		if (personDmo == null) {
			throw new ResourceNotFoundException();
		}
		personDmo.setAtomLocus(resourceRoot.getAtomLocus());
		final CaptainResource captainResource = new CaptainResource();
		captainResource.setId(resourceRoot.getResourceId());
		captainResource.setPersonDmo(personDmo);
		captainResource.setVersion(resourceRoot.getVersion());
		return captainResource;
	}
}
