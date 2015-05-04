package nl.haploid.octowight.registry.repository;

import javax.persistence.*;

@Entity
@Table(name = "resource_model", schema = "octowight")
public class ResourceModelDmo {

	@Id
	@SequenceGenerator(name = "resource_model_sequence", sequenceName = "octowight.resource_model_sequence")
	@GeneratedValue(generator = "resource_model_sequence")
	private Long id;

	@Column(name = "resource_id")
	private Long resourceId;

	@Column(name = "resource_type")
	private String resourceType;

	@Column
	private Long version;

	@Column
	private String body;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
}
