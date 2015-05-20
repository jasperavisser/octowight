package nl.haploid.octowight.sample.data

import nl.haploid.octowight.sample.{AbstractIT, TestData}
import org.springframework.beans.factory.annotation.Autowired

class CaptainModelSerializerIT extends AbstractIT {
  @Autowired private[this] val captainModelSerializer: CaptainModelSerializer = null

  "Captain model serializer" should "serialize a model" in {
    val id = TestData.nextLong
    val name = TestData.nextString
    val model: CaptainModel = new CaptainModel(id, name)
    val body = captainModelSerializer.serialize(model)
    body should include(id.toString)
    body should include(name)
  }
}
