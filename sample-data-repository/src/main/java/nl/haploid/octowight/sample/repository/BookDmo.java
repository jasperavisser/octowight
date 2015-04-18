package nl.haploid.octowight.sample.repository;

import javax.persistence.*;

@Entity
@Table(name = "book", schema = "octowight")
public class BookDmo {

	@Id
	@SequenceGenerator(name = "book_sequence", sequenceName = "octowight.book_sequence")
	@GeneratedValue(generator = "book_sequence")
	private Long id;

	@Column(name = "genre")
	private String genre;

	@Column(name = "title")
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
