package nl.haploid.octowight.registry.repository;

import nl.haploid.octowight.registry.data.Atom;
import nl.haploid.octowight.registry.data.ResourceRoot;
import org.springframework.stereotype.Component;

// TODO: test
@Component
public class ResourceElementDmoFactory {

    public ResourceElementDmo fromResourceAndAtom(final ResourceRoot resourceRoot, final Atom atom) {
        final ResourceElementDmo dmo = new ResourceElementDmo();
        dmo.setAtomId(atom.getAtomId());
        dmo.setAtomLocus(resourceRoot.getAtomLocus());
        dmo.setAtomType(atom.getAtomType());
        dmo.setResourceId(resourceRoot.getResourceId());
        dmo.setResourceType(resourceRoot.getResourceType());
        return dmo;
    }
}
