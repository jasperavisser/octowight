package nl.haploid.octowight.sample.data;

import nl.haploid.octowight.sample.repository.PersonDmo;
import org.springframework.stereotype.Component;

@Component
public class CaptainFactory {

	public Captain fromPersonDmo(final PersonDmo personDmo, final long resourceId) {
		final Captain captain = new Captain();
		captain.setId(resourceId);
		captain.setName(personDmo.getName());
		return captain;
	}
}
