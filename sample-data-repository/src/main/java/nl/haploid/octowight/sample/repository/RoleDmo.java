package nl.haploid.octowight.sample.repository;

import nl.haploid.octowight.registry.data.Atom;

import javax.persistence.*;

@Entity
@Table(name = RoleDmo.ATOM_TYPE, schema = "octowight")
public class RoleDmo implements Atom {

	public static final String ATOM_TYPE = "role";

	@Id
	@SequenceGenerator(name = "role_sequence", sequenceName = "octowight.role_sequence")
	@GeneratedValue(generator = "role_sequence")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "person")
	private PersonDmo person;

	@Column(name = "type")
	private String type;

	private String atomOrigin;

	public Long getId() {
		return id;
	}

	public PersonDmo getPerson() {
		return person;
	}

	public String getType() {
		return type;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public void setType(final String type) {
		this.type = type;
	}

	public void setPerson(PersonDmo person) {
		this.person = person;
	}

	@Override
	public Long getAtomId() {
		return getId();
	}

	@Override
	public String getAtomOrigin() {
		return atomOrigin;
	}

	public void setAtomOrigin(String atomOrigin) {
		this.atomOrigin = atomOrigin;
	}

	@Override
	public String getAtomType() {
		return ATOM_TYPE;
	}
}
