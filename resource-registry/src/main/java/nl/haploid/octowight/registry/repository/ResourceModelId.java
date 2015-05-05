package nl.haploid.octowight.registry.repository;

import java.io.Serializable;

public class ResourceModelId implements Serializable {

	private Long resourceId;

	private String resourceType;

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
}

