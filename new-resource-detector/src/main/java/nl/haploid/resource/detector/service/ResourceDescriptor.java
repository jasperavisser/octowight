package nl.haploid.resource.detector.service;

public class ResourceDescriptor {

	private Long resourceId;

	private String resourceType;

	private Long atomId;

	private String atomType; // TODO: in this context, do we speak of tables/rows? maybe atomType, atomId

	public String getKey() {
		return String.format("%s/%s->%s", getAtomType(), getAtomId(), getResourceType());
	}

	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
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
