package nl.haploid.octowight.sample.data

import nl.haploid.octowight.JsonMapper
import nl.haploid.octowight.registry.data.ModelSerializer
import org.springframework.stereotype.Component

@Component
class CaptainModelSerializer extends ModelSerializer[CaptainModel] {
  val jsonMapper = new JsonMapper

  def serialize(model: CaptainModel) = jsonMapper.serialize(model)

  def deserialize(body: String, modelClass: Class[CaptainModel]) = jsonMapper.deserialize(body, modelClass)
}
