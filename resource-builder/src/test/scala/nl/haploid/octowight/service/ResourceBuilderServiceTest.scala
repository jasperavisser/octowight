package nl.haploid.octowight.service

import nl.haploid.octowight.registry.data.{ResourceMessage, Atom, ResourceRoot}
import nl.haploid.octowight.registry.service.ResourceElementRegistryService
import nl.haploid.octowight.builder.ResourceBuilder
import nl.haploid.octowight.{AbstractTest, TestData}
import nl.haploid.octowight.{Mocked, Tested}

class ResourceBuilderServiceTest extends AbstractTest {
  @Tested private[this] val resourceBuilderService: ResourceBuilderService = null
  @Mocked private[this] val resourceBuilders: ResourceBuilders = null
  @Mocked private[this] val resourceElementRegistryService: ResourceElementRegistryService = null

  behavior of "Resource builders service"

  it should "build resources" in {
    val collection1 = TestData.nextString
    val collection2 = TestData.nextString
    val resourceBuilder1 = mock[ResourceBuilder]
    val resourceBuilder2 = mock[ResourceBuilder]
    val resourceBuilder3 = mock[ResourceBuilder]
    val resourceRoots1 = mock[Iterable[ResourceRoot]]
    val resourceRoots2 = mock[Iterable[ResourceRoot]]
    val atoms1: Iterable[Atom] = Iterable(TestData.atom, TestData.atom)
    val atoms2: Iterable[Atom] = Iterable(TestData.atom, TestData.atom)
    val atoms3: Iterable[Atom] = Iterable(TestData.atom, TestData.atom)
    val resource1: ResourceMessage = TestData.resource
    val resource2: ResourceMessage = TestData.resource
    val resource3: ResourceMessage = TestData.resource
    val resourceAndAtoms1 = (resource1, atoms1)
    val resourceAndAtoms2 = (resource2, atoms2)
    val resourceAndAtoms3 = (resource3, atoms3)
    val resourcesAndAtoms1 = Iterable(resourceAndAtoms1, resourceAndAtoms2)
    val resourcesAndAtoms2 = Iterable()
    val resourcesAndAtoms3 = Iterable(resourceAndAtoms3)
    val resourceRootsByCollection: Map[String, Iterable[ResourceRoot]] = Map((collection1, resourceRoots1), (collection2, resourceRoots2))
    val expectedResources = Iterable(resource1, resource2, resource3)
    expecting {
      resourceBuilders.forCollection(collection1) andReturn List(resourceBuilder1) once()
      resourceBuilder1.build(resourceRoots1) andReturn resourcesAndAtoms1 once()
      resourceBuilders.forCollection(collection2) andReturn List(resourceBuilder2, resourceBuilder3) once()
      resourceBuilder2.build(resourceRoots2) andReturn resourcesAndAtoms2 once()
      resourceBuilder3.build(resourceRoots2) andReturn resourcesAndAtoms3 once()
      resourceElementRegistryService.saveResourceElements(resource1.resourceIdentifier, atoms1) andVoid() once()
      resourceElementRegistryService.saveResourceElements(resource2.resourceIdentifier, atoms2) andVoid() once()
      resourceElementRegistryService.saveResourceElements(resource3.resourceIdentifier, atoms3) andVoid() once()
    }
    whenExecuting(resourceBuilders, resourceBuilder1, resourceBuilder2, resourceBuilder3) {
      val actualResources = resourceBuilderService.buildResources(resourceRootsByCollection)
      actualResources should be(expectedResources)
    }
  }
}
