package nl.haploid.octowight.sample.detector;

import nl.haploid.octowight.AtomChangeEvent;
import nl.haploid.octowight.detector.ResourceDetector;
import nl.haploid.octowight.registry.data.ResourceRoot;
import nl.haploid.octowight.registry.data.ResourceRootFactory;
import nl.haploid.octowight.sample.repository.RoleDmo;
import nl.haploid.octowight.sample.repository.RoleDmoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class CaptainResourceDetector implements ResourceDetector {

	protected static final String ROLE_TYPE = "captain";

	protected static final String RESOURCE_TYPE = "captain";

	@Autowired
	private RoleDmoRepository repository;

	@Autowired
	private ResourceRootFactory resourceRootFactory;

	@Override
	public Collection<String> getAtomTypes() {
		return Collections.singletonList(RoleDmo.ATOM_TYPE);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ResourceRoot> detect(final List<AtomChangeEvent> events) {
		final Map<Long, RoleDmo> rolesByAtomId = getRolesById(events);
		return events.stream()
				.filter(event -> rolesByAtomId.containsKey(event.getAtomId()))
				.filter(event -> isCaptain(rolesByAtomId.get(event.getAtomId())))
				.map(event -> resourceRootFactory.fromAtomChangeEvent(event, RESOURCE_TYPE))
				.collect(Collectors.toList());
	}

	protected Map<Long, RoleDmo> getRolesById(final List<AtomChangeEvent> events) {
		final List<Long> roleIds = events.stream()
				.map(AtomChangeEvent::getAtomId)
				.collect(Collectors.toList());
		return repository.findAll(roleIds).stream()
				.collect(Collectors.toMap(RoleDmo::getId, Function.identity()));
	}

	protected boolean isCaptain(final RoleDmo roleDmo) {
		return ROLE_TYPE.equals(roleDmo.getType());
	}
}
