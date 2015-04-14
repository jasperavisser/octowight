package nl.haploid.octowight.data;

public class ResourceCoreAtom {

	private Long resourceId;

	private String resourceType;

	private Long atomId;

	private String atomLocus;

	private String atomType;

	public String key() {
		return String.format("%s:%s/%s->%s", getAtomLocus(), getAtomType(), getAtomId(), getResourceType());
	}

	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(final Long resourceId) {
		this.resourceId = resourceId;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(final String resourceType) {
		this.resourceType = resourceType;
	}

	public Long getAtomId() {
		return atomId;
	}

	public void setAtomId(final Long atomId) {
		this.atomId = atomId;
	}

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
}
