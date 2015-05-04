package nl.haploid.octowight.registry.data;

import java.util.Collection;

public abstract class Resource<T extends Model> {

	private Long version;

	public abstract Collection<Atom> getAtoms();

	public abstract Long getId();

	public abstract String getType();

	public abstract T getModel();

	public Long getVersion() {
		return version;
	}

	public void setVersion(final Long version) {
		this.version = version;
	}
}
