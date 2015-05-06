package nl.haploid.octowight.registry.data;

public class ResourceRoot {

	private Long resourceId;

	private String resourceType;

	private Long atomId;

	private String atomOrigin;

	private String atomType;

	private Long version;

	public String key() {
		return String.format("%s:%s/%s->%s", getAtomOrigin(), getAtomType(), getAtomId(), getResourceType());
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

	public Long getVersion() {
		return version;
	}

	public void setVersion(final Long version) {
		this.version = version;
	}
}
