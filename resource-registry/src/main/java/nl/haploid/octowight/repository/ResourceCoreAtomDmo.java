package nl.haploid.octowight.repository;

import javax.persistence.*;

// TODO: there has to be a better name!!!!!
@Entity
@Table(name = "resource_core_atom", schema = "octowight")
public class ResourceCoreAtomDmo {

	@Id
	@Column(name = "resource_id")
	@SequenceGenerator(name = "resource_sequence", sequenceName = "octowight.resource_sequence")
	@GeneratedValue(generator = "resource_sequence")
	private Long resourceId;

	@Column(name = "resource_type")
	private String resourceType;

	@Column(name = "atom_id")
	private Long atomId;

	@Column(name = "atom_locus")
	private String atomLocus;

	@Column(name = "atom_type")
	private String atomType;

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
}
