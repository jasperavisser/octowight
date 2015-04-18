package nl.haploid.octowight.repository;

import javax.persistence.*;

@Entity
@Table(name = "atom_change_events", schema = "octowight")
public class AtomChangeEventDmo {

	@Id
	@SequenceGenerator(name = "event_sequence", sequenceName = "octowight.event_sequence")
	@GeneratedValue(generator = "event_sequence")
	private Long id;

	@Column(name = "atom_id")
	private Long atomId;

	@Column(name = "atom_locus")
	private String atomLocus;

	@Column(name = "atom_type")
	private String atomType;

	public Long getId() {
		return id;
	}

	public Long getAtomId() {
		return atomId;
	}

	public String getAtomLocus() {
		return atomLocus;
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

	public void setAtomLocus(final String atomLocus) {
		this.atomLocus = atomLocus;
	}
}
