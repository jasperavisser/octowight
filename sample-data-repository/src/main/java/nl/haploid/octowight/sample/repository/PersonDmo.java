package nl.haploid.octowight.sample.repository;

import nl.haploid.octowight.registry.data.Atom;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = PersonDmo.ATOM_TYPE, schema = "octowight")
public class PersonDmo implements Atom {

    public static final String ATOM_TYPE = "person";

    @Id
    @SequenceGenerator(name = "person_sequence", sequenceName = "octowight.person_sequence")
    @GeneratedValue(generator = "person_sequence")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "person")
    private List<RoleDmo> roles = new ArrayList<>();
    private String atomLocus;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<RoleDmo> getRoles() {
        return this.roles;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setRoles(final List<RoleDmo> roles) {
        this.roles = roles;
    }

    @Override
    public Long getAtomId() {
        return getId();
    }

    @Override
    public String getAtomLocus() {
        return atomLocus;
    }

    public void setAtomLocus(String atomLocus) {
        this.atomLocus = atomLocus;
    }

    @Override
    public String getAtomType() {
        return ATOM_TYPE;
    }
}
