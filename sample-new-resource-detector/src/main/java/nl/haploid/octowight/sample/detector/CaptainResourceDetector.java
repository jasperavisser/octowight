package nl.haploid.octowight.sample.detector;

import nl.haploid.octowight.AtomChangeEvent;
import nl.haploid.octowight.detector.ResourceDetector;
import nl.haploid.octowight.registry.data.Resource;
import nl.haploid.octowight.registry.data.ResourceFactory;
import nl.haploid.octowight.sample.repository.PersonDmo;
import nl.haploid.octowight.sample.repository.PersonDmoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class CaptainResourceDetector implements ResourceDetector {

	private Logger log = LoggerFactory.getLogger(getClass());

	protected static final String ROLE_TYPE = "captain";

	protected static final String RESOURCE_TYPE = "captain";

	@Autowired
	private PersonDmoRepository repository;

	@Autowired
	private ResourceFactory resourceFactory;

	@Override
	public Collection<String> getAtomTypes() {
		return Collections.singletonList(PersonDmo.ATOM_TYPE);
	}

	@Override
	public List<Resource> detect(final List<AtomChangeEvent> events) {
		final Map<Long, PersonDmo> personsByAtomId = getPersonsById(events);
		return events.stream()
				.filter(event -> personsByAtomId.containsKey(event.getAtomId()))
				.filter(event -> isCaptain(personsByAtomId.get(event.getAtomId())))
				.map(event -> resourceFactory.fromAtomChangeEvent(event, RESOURCE_TYPE))
				.collect(Collectors.toList());
	}

	protected Map<Long, PersonDmo> getPersonsById(final List<AtomChangeEvent> events) {
		final List<Long> personIds = events.stream()
				.map(AtomChangeEvent::getAtomId)
				.collect(Collectors.toList());
		return repository.findAll(personIds).stream()
				.collect(Collectors.toMap(PersonDmo::getId, Function.identity()));
	}

	protected boolean isCaptain(final PersonDmo personDmo) {
		return personDmo.getRoles().stream()
				.filter(roleDmo -> ROLE_TYPE.equals(roleDmo.getType()))
				.count() > 0;
	}
}
