package nl.haploid.octowight.registry.repository;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "resourceElement")
public class ResourceElementDmo {

	@Id
	private String id;

	private Long resourceId;

	private String resourceType;

	private Long atomId;

	private String atomOrigin;

	private String atomType;

	public String getId() {
		return id;
	}

	public Long getResourceId() {
		return resourceId;
	}

	public void setId(final String id) {
		this.id = id;
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

	@Override
	public boolean equals(final Object that) {
		return EqualsBuilder.reflectionEquals(this, that, false);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, false);
	}
}
