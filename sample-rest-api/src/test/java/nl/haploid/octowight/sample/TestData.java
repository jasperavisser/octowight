package nl.haploid.octowight.sample;

import nl.haploid.octowight.registry.data.ResourceRoot;
import nl.haploid.octowight.registry.repository.ResourceRootDmo;
import nl.haploid.octowight.sample.data.CaptainModel;
import nl.haploid.octowight.sample.repository.PersonDmo;
import nl.haploid.octowight.sample.repository.RoleDmo;

import java.util.Random;
import java.util.UUID;

public class TestData {

    public static PersonDmo personDmo() {
        return personDmo(null);
    }

    public static PersonDmo personDmo(final Long id) {
        final PersonDmo person = new PersonDmo();
        person.setId(id);
        person.setName(nextString());
        return person;
    }

    public static String nextString() {
        return UUID.randomUUID().toString();
    }

    public static long nextLong() {
        return new Random().nextLong();
    }

    public static ResourceRoot resourceRoot() {
        return resourceRoot(nextLong());
    }

    public static ResourceRoot resourceRoot(final Long resourceId) {
        final ResourceRoot resourceRoot = new ResourceRoot();
        resourceRoot.setAtomId(nextLong());
        resourceRoot.setAtomLocus(nextString());
        resourceRoot.setAtomType(nextString());
        resourceRoot.setResourceId(resourceId);
        resourceRoot.setResourceType(nextString());
        return resourceRoot;
    }

    public static CaptainModel captainModel() {
        return new CaptainModel();
    }

    public static ResourceRootDmo resourceRootDmo() {
        return new ResourceRootDmo();
    }

    public static RoleDmo roleDmo(final PersonDmo personDmo, final String type) {
        final RoleDmo dmo = new RoleDmo();
        dmo.setId(nextLong());
        dmo.setPerson(personDmo);
        dmo.setType(type);
        return dmo;
    }
}
