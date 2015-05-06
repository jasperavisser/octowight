package nl.haploid.octowight;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.codehaus.jackson.annotate.JsonIgnore;

public class AtomChangeEvent {

	private Long id;

	private Long atomId;

	private String atomOrigin;

	private String atomType;

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public Long getAtomId() {
		return atomId;
	}

	public void setAtomId(final Long atomId) {
		this.atomId = atomId;
	}

	public String getAtomOrigin() {
		return atomOrigin;
	}

	public void setAtomOrigin(final String atomOrigin) {
		this.atomOrigin = atomOrigin;
	}

	public String getAtomType() {
		return atomType;
	}

	public void setAtomType(final String atomType) {
		this.atomType = atomType;
	}

	@JsonIgnore
	public AtomGroup getAtomGroup() {
		final AtomGroup atomGroup = new AtomGroup();
		atomGroup.setAtomOrigin(getAtomOrigin());
		atomGroup.setAtomType(getAtomType());
		return atomGroup;
	}

	@Override
	public boolean equals(final Object that) {
		return EqualsBuilder.reflectionEquals(this, that, false);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, false);
	}
}
