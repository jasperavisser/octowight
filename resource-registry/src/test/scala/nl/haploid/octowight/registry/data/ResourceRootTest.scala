package nl.haploid.octowight.registry.data

import nl.haploid.octowight.registry.AbstractTest

class ResourceRootTest extends AbstractTest {

  behavior of "Resource root"

  it should "return a key" in {
    val atom = new Atom(69l, "california", "campbell")
    val resourceRoot = new ResourceRoot(resourceId = null, resourceCollection = "cosgrove", root = atom, version = null)
    val expectedKey = "california:campbell/69->cosgrove"
    val actualKey = resourceRoot.key
    actualKey should be(expectedKey)
  }
}
