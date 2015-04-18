package nl.haploid.octowight.sample.data;

public class Book {

	private Long id;

	private String genre;

	private String title;

	public Long getId() {
		return id;
	}

	public String getGenre() {
		return genre;
	}

	public String getTitle() {
		return title;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public void setGenre(final String genre) {
		this.genre = genre;
	}

	public void setTitle(final String title) {
		this.title = title;
	}
}
