package nl.haploid.resource.detector.service;

public class ResourceDescriptor {

	private Long resourceId;

	private String resourceType;

	private Long atomId;

	private String atomLocus;

	private String atomType;

	public String getKey() {
		return String.format("%s:%s/%s->%s", getAtomLocus(), getAtomType(), getAtomId(), getResourceType());
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

	public String getAtomLocus() {
		return atomLocus;
	}

	public void setAtomLocus(String atomLocus) {
		this.atomLocus = atomLocus;
	}

	public String getAtomType() {
		return atomType;
	}

	public void setAtomType(String atomType) {
		this.atomType = atomType;
	}
}
