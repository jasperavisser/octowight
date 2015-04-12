package nl.haploid.event.channel.repository;

import javax.persistence.*;

@Entity
@Table(name = "atom_change_events", schema = "octowight")
public class AtomChangeEventDmo {

	@Id
	@SequenceGenerator(name = "event_sequence", schema = "octowight")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(name = "atom_type")
	private String atomType;

	@Column(name = "atom_id")
	private Long atomId;

	public Long getId() {
		return id;
	}

	public Long getAtomId() {
		return atomId;
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
}
