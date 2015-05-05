package nl.haploid.octowight.registry.repository;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "resourceRoot")
public class ResourceRootDocument {

	@Id
	private String id;

	private Long resourceId; // TODO: generated

	private String resourceType;

	private Long atomId;

	private String atomLocus;

	private String atomType;

	private Long version;  // TODO: generated

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
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

	public Long getVersion() {
		return version;
	}

	public void setVersion(final Long version) {
		this.version = version;
	}
}
