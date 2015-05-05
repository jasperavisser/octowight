package nl.haploid.octowight.registry.repository;

import nl.haploid.octowight.registry.data.Resource;
import org.springframework.stereotype.Component;

@Component
public class ResourceModelDocumentFactory {

	// TODO: test
	public ResourceModelDmo fromResourceAndBody(final Resource resource, final String body) {
		final ResourceModelDmo resourceModelDmo = new ResourceModelDmo();
		final ResourceModelId resourceModelId = new ResourceModelId();
		resourceModelId.setResourceId(resource.getId());
		resourceModelId.setResourceType(resource.getType());
		resourceModelDmo.setId(resourceModelId);
		resourceModelDmo.setBody(body);
		resourceModelDmo.setVersion(resource.getVersion());
		return resourceModelDmo;
	}
}
