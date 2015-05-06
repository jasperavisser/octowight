package nl.haploid.octowight.registry.repository;

import nl.haploid.octowight.registry.data.Atom;
import nl.haploid.octowight.registry.data.Resource;
import org.springframework.stereotype.Component;

@Component
public class ResourceElementDmoFactory {

	public ResourceElementDmo fromResourceAndAtom(final Resource resource, final Atom atom) {
		final ResourceElementDmo dmo = new ResourceElementDmo();
		dmo.setAtomId(atom.getAtomId());
		dmo.setAtomOrigin(atom.getAtomOrigin());
		dmo.setAtomType(atom.getAtomType());
		dmo.setResourceId(resource.getId());
		dmo.setResourceType(resource.getType());
		return dmo;
	}
}
