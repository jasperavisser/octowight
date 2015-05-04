package nl.haploid.octowight.registry.repository;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;

@Entity
@Table(name = "resource_element", schema = "octowight")
public class ResourceElementDmo {

	@Id
	@SequenceGenerator(name = "resource_element_sequence", sequenceName = "octowight.resource_element_sequence")
	@GeneratedValue(generator = "resource_element_sequence")
	private Long id;

	@Column(name = "resource_id")
	private Long resourceId;

	@Column(name = "resource_type")
	private String resourceType;

	@Column(name = "atom_id")
	private Long atomId;

	@Column(name = "atom_locus")
	private String atomLocus;

	@Column(name = "atom_type")
	private String atomType;

	public Long getId() {
		return id;
	}

	public Long getResourceId() {
		return resourceId;
	}

	public void setId(Long id) {
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

	@Override
	public boolean equals(final Object that) {
		return EqualsBuilder.reflectionEquals(this, that, false);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, false);
	}
}
