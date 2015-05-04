package nl.haploid.octowight.registry.repository;

import nl.haploid.octowight.registry.data.Resource;
import org.springframework.stereotype.Component;

@Component
public class ResourceModelDmoFactory {

	public ResourceModelDmo fromResourceAndBody(final Resource resource, final String body) {
		final ResourceModelDmo dmo = new ResourceModelDmo();
		dmo.setResourceId(resource.getId());
		dmo.setResourceType(resource.getType());
		dmo.setBody(body);
		dmo.setVersion(resource.getVersion());
		return dmo;
	}
}
