package nl.haploid.octowight.sample.repository;

import javax.persistence.*;

@Entity
@Table(name = "atom_change_event", schema = "octowight")
public class AtomChangeEventDmo {

	@Id
	@SequenceGenerator(name = "event_sequence", sequenceName = "octowight.event_sequence")
	@GeneratedValue(generator = "event_sequence")
	private Long id;

	@Column(name = "atom_id")
	private Long atomId;

	@Column(name = "atom_origin")
	private String atomOrigin;

	@Column(name = "atom_type")
	private String atomType;

	public Long getId() {
		return id;
	}

	public Long getAtomId() {
		return atomId;
	}

	public String getAtomOrigin() {
		return atomOrigin;
	}

	public String getAtomType() {
		return atomType;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public void setAtomId(final Long atomId) {
		this.atomId = atomId;
	}

	public void setAtomType(final String atomType) {
		this.atomType = atomType;
	}

	public void setAtomOrigin(final String atomOrigin) {
		this.atomOrigin = atomOrigin;
	}
}
