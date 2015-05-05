package nl.haploid.octowight.registry.repository;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "resourceModel")
public class ResourceModelDmo {

	@Id
	private ResourceModelId id;

	private Long version;

	private String body;

	public ResourceModelId getId() {
		return id;
	}

	public void setId(final ResourceModelId id) {
		this.id = id;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(final Long version) {
		this.version = version;
	}

	public String getBody() {
		return body;
	}

	public void setBody(final String body) {
		this.body = body;
	}
}
