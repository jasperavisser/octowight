package nl.haploid.octowight.sample.data;

import nl.haploid.octowight.registry.data.ResourceRoot;
import nl.haploid.octowight.sample.repository.PersonDmo;
import nl.haploid.octowight.sample.repository.RoleDmo;
import nl.haploid.octowight.sample.repository.RoleDmoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CaptainResourceFactory implements ResourceFactory<CaptainResource> {

	@Autowired
	private RoleDmoRepository roleDmoRepository;

	@Override
	@Transactional(readOnly = true)
	public CaptainResource fromResourceRoot(final ResourceRoot resourceRoot) {
		final RoleDmo roleDmo = roleDmoRepository.findOne(resourceRoot.getAtomId());
		if (roleDmo == null) {
			throw new ResourceNotFoundException();
		}
		roleDmo.setAtomLocus(resourceRoot.getAtomLocus());
		final PersonDmo personDmo = roleDmo.getPerson();
		personDmo.setAtomLocus(resourceRoot.getAtomLocus());
		final CaptainResource captainResource = new CaptainResource();
		captainResource.setId(resourceRoot.getResourceId());
		captainResource.setPersonDmo(personDmo);
		captainResource.setRoleDmo(roleDmo);
		captainResource.setVersion(resourceRoot.getVersion());
		return captainResource;
	}
}
