package nl.haploid.octowight.sample.data

import nl.haploid.octowight.JsonMapper
import nl.haploid.octowight.registry.data.{Model, ModelSerializer}
import org.springframework.stereotype.Component

@Component
class JsonModelSerializer[M <: Model] extends ModelSerializer[M] {
  val jsonMapper = new JsonMapper

  def serialize(model: M) = jsonMapper.serialize(model)

  def deserialize(body: String, modelClass: Class[M]) = jsonMapper.deserialize(body, modelClass)
}
