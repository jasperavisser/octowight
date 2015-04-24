package nl.haploid.octowight.sample.data;

import nl.haploid.octowight.registry.data.Model;

public class CaptainModel implements Model {

	private Long id;

	private String name;

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public void setName(final String name) {
		this.name = name;
	}
}
