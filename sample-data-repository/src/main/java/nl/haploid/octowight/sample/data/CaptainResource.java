package nl.haploid.octowight.sample.data;

import nl.haploid.octowight.registry.data.Atom;
import nl.haploid.octowight.registry.data.Resource;
import nl.haploid.octowight.sample.repository.PersonDmo;
import nl.haploid.octowight.sample.repository.RoleDmo;

import java.util.Arrays;
import java.util.Collection;

// TODO: new project with resource stuff?
public class CaptainResource extends Resource<CaptainModel> {

    public static final String RESOURCE_TYPE = "captain";

    private PersonDmo personDmo;

    private RoleDmo roleDmo;

    private Long id;

    @Override
    public Collection<Atom> getAtoms() {
        return Arrays.asList(personDmo, roleDmo);
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public void setPersonDmo(final PersonDmo personDmo) {
        this.personDmo = personDmo;
    }

    public void setRoleDmo(final RoleDmo roleDmo) {
        this.roleDmo = roleDmo;
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
}
