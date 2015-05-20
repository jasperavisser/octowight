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
      roleDmoRepository.findOne(resourceRoot.root.id) andReturn roleDmo once()
      roleDmo.setOrigin(resourceRoot.root.origin) once()
      roleDmo.getPerson andReturn personDmo once()
      roleDmo.toAtom andReturn roleAtom once()
    }
    whenExecuting(roleDmo, roleDmoRepository) {
      val captainResource = captainResourceFactory.fromResourceRoot(resourceRoot).getOrElse(fail())
      captainResource.id should be(resourceRoot.resourceId)
      captainResource.atoms should have size 2
      captainResource.version should be(resourceRoot.version)
      captainResource.model.id should be(personDmo.getId)
      captainResource.model.name should be(personDmo.getName)
    }
  }
}
