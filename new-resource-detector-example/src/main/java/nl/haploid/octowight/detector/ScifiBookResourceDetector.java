package nl.haploid.octowight.detector;

import nl.haploid.octowight.AtomChangeEvent;
import nl.haploid.octowight.data.ResourceCoreAtom;
import nl.haploid.octowight.data.ResourceCoreAtomFactory;
import nl.haploid.octowight.repository.BookDmo;
import nl.haploid.octowight.repository.BookDmoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ScifiBookResourceDetector implements ResourceDetector {

	public static final String ATOM_TYPE = "book";

	public static final String GENRE = "scifi";

	private static final String RESOURCE_TYPE = "scifi-book";

	@Autowired
	private BookDmoRepository repository;

	@Override
	public Collection<String> getAtomTypes() {
		return Collections.singletonList(ATOM_TYPE);
	}

	@Override
	public List<ResourceCoreAtom> detect(final List<AtomChangeEvent> events) {
		final Map<Long, BookDmo> booksByAtomId = getBooksById(events);
		return events.stream()
				.filter(event -> booksByAtomId.containsKey(event.getAtomId()))
				.filter(event -> GENRE.equals(booksByAtomId.get(event.getAtomId()).getGenre()))
				.map(event -> ResourceCoreAtomFactory.fromAtomChangeEvent(event, RESOURCE_TYPE))
				.collect(Collectors.toList());
	}

	protected Map<Long, BookDmo> getBooksById(final List<AtomChangeEvent> events) {
		final List<Long> bookIds = events.stream()
				.map(AtomChangeEvent::getAtomId)
				.collect(Collectors.toList());
		return repository.findAll(bookIds).stream()
				.collect(Collectors.toMap(BookDmo::getId, Function.identity()));
	}
}
