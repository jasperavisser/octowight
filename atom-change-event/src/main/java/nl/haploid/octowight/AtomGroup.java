package nl.haploid.octowight;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class AtomGroup {

	private String atomOrigin;

	private String atomType;

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

	@Override
	public boolean equals(final Object that) {
		return EqualsBuilder.reflectionEquals(this, that, false);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, false);
	}
}
