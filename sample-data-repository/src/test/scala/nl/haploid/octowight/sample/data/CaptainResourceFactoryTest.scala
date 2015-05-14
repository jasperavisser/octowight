package nl.haploid.octowight.sample.data

import nl.haploid.octowight.registry.data.Atom
import nl.haploid.octowight.sample.repository.{RoleDmo, RoleDmoRepository}
import nl.haploid.octowight.sample.{AbstractTest, TestData}
import nl.haploid.octowight.{Mocked, Tested}

class CaptainResourceFactoryTest extends AbstractTest {
  @Tested private[this] val captainResourceFactory: CaptainResourceFactory = null
  @Mocked private[this] val roleDmoRepository: RoleDmoRepository = null

  "Captain resource factory" should "build from a resource root" in {
    val roleDmo = mock[RoleDmo]
    val resourceRoot = TestData.resourceRoot
    val personDmo = TestData.personDmo
    val roleAtom = mock[Atom]
    expecting {
      roleDmoRepository.findOne(resourceRoot.getAtomId) andReturn roleDmo once()
      roleDmo.setOrigin(resourceRoot.getAtomOrigin) once()
      roleDmo.getPerson andReturn personDmo once()
      roleDmo.toAtom andReturn roleAtom once()
    }
    whenExecuting(roleDmo, roleDmoRepository) {
      val captainResource = captainResourceFactory.fromResourceRoot(resourceRoot).getOrElse(fail())
      captainResource.getId should be(resourceRoot.getResourceId)
      captainResource.getAtoms should have size 2
      captainResource.getVersion should be(resourceRoot.getVersion)
    }
  }
}
