package nl.haploid.event;

public class AtomChangeEvent {

	private Long id;

	private String atomType;

	private Long atomId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAtomId() {
		return atomId;
	}

	public void setAtomId(Long atomId) {
		this.atomId = atomId;
	}

	public String getAtomType() {
		return atomType;
	}

	public void setAtomType(String atomType) {
		this.atomType = atomType;
	}
}
