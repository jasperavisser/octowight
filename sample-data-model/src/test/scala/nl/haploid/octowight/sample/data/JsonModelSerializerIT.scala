package nl.haploid.octowight.sample.data

import nl.haploid.octowight.sample.AbstractIT
import org.springframework.beans.factory.annotation.Autowired

class JsonModelSerializerIT extends AbstractIT {

  @Autowired private[this] val captainModelSerializer: JsonModelSerializer[CaptainModel] = null

  behavior of "JSON model serializer"

  it should "serialize a model" in {
    val id = 1234L
    val name = "Someone"
    val model: CaptainModel = new CaptainModel(id, name)
    val body = captainModelSerializer.serialize(model)
    body should include(id.toString)
    body should include(name)
  }
}
