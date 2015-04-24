package nl.haploid.octowight.sample.data;

import nl.haploid.octowight.registry.data.Atom;
import nl.haploid.octowight.registry.data.Resource;
import nl.haploid.octowight.sample.repository.PersonDmo;

import java.util.Collection;
import java.util.Collections;

public class CaptainResource implements Resource<CaptainModel> {

    public static final String RESOURCE_TYPE = "captain";

    private PersonDmo personDmo;

    private Long id;

    private Long version;

    @Override
    public Collection<Atom> getAtoms() {
        return Collections.singletonList(personDmo);
    }

    @Override
    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public void setPersonDmo(final PersonDmo personDmo) {
        this.personDmo = personDmo;
    }

    @Override
    public String getType() {
        return RESOURCE_TYPE;
    }

    @Override
    public CaptainModel getModel() {
        final CaptainModel captainModel = new CaptainModel();
        captainModel.setId(personDmo.getId());
        captainModel.setName(personDmo.getName());
        return captainModel;
    }

    @Override
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
