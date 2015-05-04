package nl.haploid.octowight;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class AtomGroup {

	private String atomLocus;

	private String atomType;

	public String getAtomLocus() {
		return atomLocus;
	}

	public void setAtomLocus(final String atomLocus) {
		this.atomLocus = atomLocus;
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
