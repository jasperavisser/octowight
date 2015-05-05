package nl.haploid.octowight.registry.service;

import nl.haploid.octowight.AtomChangeEvent;
import nl.haploid.octowight.AtomGroup;
import nl.haploid.octowight.registry.AbstractIT;
import nl.haploid.octowight.registry.TestData;
import nl.haploid.octowight.registry.data.ResourceRoot;
import nl.haploid.octowight.registry.data.ResourceRootFactory;
import nl.haploid.octowight.registry.repository.ResourceElementDmoRepository;
import nl.haploid.octowight.registry.repository.ResourceRootDmo;
import nl.haploid.octowight.registry.repository.ResourceRootDmoRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class ResourceRegistryServiceIT extends AbstractIT {

	@Autowired
	private ResourceElementDmoRepository resourceElementDmoRepository;

	@Autowired
	private ResourceRegistryService resourceRegistryService;

	@Autowired
	private ResourceRootDmoRepository resourceRootDmoRepository;

	@Autowired
	private ResourceRootFactory resourceRootFactory;

	@Test
	public void testIsNewResource() {
		final ResourceRootDmo resourceRootDmo = resourceRootDmoRepository.save(TestData.resourceRootDmo());
		final ResourceRoot resourceRoot = resourceRootFactory.fromResourceRootDmo(resourceRootDmo);
		final boolean isNewResource = resourceRegistryService.isNewResource(resourceRoot);
		assertFalse(isNewResource);
	}

	@Test
	public void testSaveNewResource() {
		final ResourceRoot resourceRoot = resourceRegistryService.saveNewResource(TestData.resourceRoot());
		assertNotNull(resourceRoot.getResourceId());
		assertNotNull(resourceRoot.getVersion());
	}

	@Test
	public void testMarkResourcesDirty() {
		final ResourceRootDmo resourceRootDmo = resourceRootDmoRepository.save(TestData.resourceRootDmo());
		final AtomGroup atomGroup = TestData.atomGroup();
		final AtomChangeEvent atomChangeEvent1 = TestData.atomChangeEvent(atomGroup);
		final AtomChangeEvent atomChangeEvent2 = TestData.atomChangeEvent(atomGroup);
		final List<AtomChangeEvent> atomChangeEvents = Arrays.asList(atomChangeEvent1, atomChangeEvent2);
		resourceElementDmoRepository.save(TestData.resourceElementDmo(resourceRootDmo, atomChangeEvent1));
		final List<ResourceRoot> resourceRoots = resourceRegistryService.markResourcesDirty(atomGroup, atomChangeEvents);
		assertEquals(1, resourceRoots.size());
	}
}
