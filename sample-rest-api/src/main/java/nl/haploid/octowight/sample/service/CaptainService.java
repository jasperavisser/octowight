package nl.haploid.octowight.sample.service;

import nl.haploid.octowight.registry.data.ResourceRoot;
import nl.haploid.octowight.sample.data.CaptainResource;
import nl.haploid.octowight.sample.data.CaptainResourceFactory;
import nl.haploid.octowight.sample.data.CaptainModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CaptainService extends AbstractResourceService<CaptainModel> {

    @Autowired
    private CaptainResourceFactory captainResourceFactory;

    // TODO: check if it is still a resource (captain resource detector)
    @Transactional("registryTransactionManager")
    public CaptainModel getCaptain(final long resourceId) {
        final ResourceRoot resourceRoot = getResourceRoot(CaptainResource.RESOURCE_TYPE, resourceId);
        // TODO: read model from cache, check version
        final CaptainResource captainResource = captainResourceFactory.fromResourceRoot(resourceRoot);
        saveResourceElements(captainResource);
        final CaptainModel captainModel = captainResource.getModel();
        final String body = getModelSerializer().toString(captainModel);
        saveModel(captainResource, body);
        return captainModel;
    }
}
