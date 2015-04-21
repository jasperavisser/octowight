package nl.haploid.octowight.registry.repository;

import nl.haploid.octowight.registry.data.Atom;
import nl.haploid.octowight.registry.data.Resource;
import org.springframework.stereotype.Component;

// TODO: test
@Component
public class ResourceElementDmoFactory {

    public ResourceElementDmo fromResourceAndAtom(final Resource resource, final Atom atom) {
        final ResourceElementDmo dmo = new ResourceElementDmo();
        dmo.setAtomId(atom.getAtomId());
        dmo.setAtomLocus(resource.getAtomLocus());
        dmo.setAtomType(atom.getAtomType());
        dmo.setResourceId(resource.getResourceId());
        dmo.setResourceType(resource.getResourceType());
        return dmo;
    }
}
