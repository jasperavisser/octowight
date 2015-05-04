package nl.haploid.octowight.registry.repository;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class VersionDmo {

	@Id
	@Column
	private Long version;

	public Long getVersion() {
		return version;
	}

	public void setVersion(final Long version) {
		this.version = version;
	}
}
