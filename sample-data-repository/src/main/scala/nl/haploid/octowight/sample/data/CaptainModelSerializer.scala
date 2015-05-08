package nl.haploid.octowight.sample.data

import nl.haploid.octowight.JsonMapper
import nl.haploid.octowight.registry.data.ModelSerializer
import org.springframework.stereotype.Component

@Component
class CaptainModelSerializer extends ModelSerializer[CaptainModel] {
  def serialize(model: CaptainModel) = {
    val jsonMapper = new JsonMapper
    jsonMapper.serialize(model)
  }

  def deserialize(body: String, modelClass: Class[CaptainModel]) = {
    val jsonMapper = new JsonMapper
    jsonMapper.deserialize(body, modelClass)
  }
}
