package nl.haploid.octowight.sample.builder

import nl.haploid.octowight.sample.data.CaptainModel
import nl.haploid.octowight.sample.repository.{PersonDmo, RoleDmo, RoleDmoRepository}
import nl.haploid.octowight.sample.{AbstractTest, TestData}
import nl.haploid.octowight.{JsonMapper, Mocked, Tested}
import org.easymock.EasyMock

class CaptainResourceBuilderTest extends AbstractTest {
  @Tested private[this] val captainResourceBuilder: CaptainResourceBuilder = null
  @Mocked private[this] val roleDmoRepository: RoleDmoRepository = null
  @Mocked private[this] val jsonMapper: JsonMapper = null

  it should "build resources" in {
    val resourceRoot1 = TestData.resourceRoot
    val resourceRoot2 = TestData.resourceRoot
    val resourceRoots = List(resourceRoot1, resourceRoot2)
    val roleDmo1 = mock[RoleDmo]
    val roleDmo2 = mock[RoleDmo]
    val personDmo1 = mock[PersonDmo]
    val personDmo2 = mock[PersonDmo]
    val model1 = TestData.nextString
    val model2 = TestData.nextString
    val atom1 = TestData.atom
    val atom2 = TestData.atom
    val atom3 = TestData.atom
    val atom4 = TestData.atom
    expecting {
      roleDmoRepository.findOne(resourceRoot1.root.id) andReturn roleDmo1 once()
      roleDmo1.setOrigin(resourceRoot1.root.origin) andVoid() once()
      roleDmo1.getPerson andReturn personDmo1 once()
      personDmo1.setOrigin(resourceRoot1.root.origin) andVoid() once()
      personDmo1.getId andReturn TestData.nextLong once()
      personDmo1.getName andReturn TestData.nextString once()
      jsonMapper.serialize(EasyMock.anyObject(classOf[CaptainModel])) andReturn model1 once()
      personDmo1.toAtom andReturn atom1 once()
      roleDmo1.toAtom andReturn atom2 once()
      roleDmoRepository.findOne(resourceRoot2.root.id) andReturn roleDmo2 once()
      roleDmo2.setOrigin(resourceRoot2.root.origin) andVoid() once()
      roleDmo2.getPerson andReturn personDmo2 once()
      personDmo2.setOrigin(resourceRoot2.root.origin) andVoid() once()
      personDmo2.getId andReturn TestData.nextLong once()
      personDmo2.getName andReturn TestData.nextString once()
      jsonMapper.serialize(EasyMock.anyObject(classOf[CaptainModel])) andReturn model2 once()
      personDmo2.toAtom andReturn atom3 once()
      roleDmo2.toAtom andReturn atom4 once()
    }
    whenExecuting(roleDmoRepository, jsonMapper, roleDmo1, roleDmo2, personDmo1, personDmo2) {
      captainResourceBuilder.build(resourceRoots)
    }
  }
}
