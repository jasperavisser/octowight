package nl.haploid.octowight.registry.data

import nl.haploid.octowight.registry.AbstractTest

class ResourceRootTest extends AbstractTest {

  "Resource root" should "return a key" in {
    val resourceRoot = new ResourceRoot
    resourceRoot.setResourceType("cosgrove")
    resourceRoot.setAtomId(69l)
    resourceRoot.setAtomOrigin("california")
    resourceRoot.setAtomCategory("campbell")
    val expectedKey = "california:campbell/69->cosgrove"
    val actualKey = resourceRoot.key
    actualKey should be(expectedKey)
  }
}
