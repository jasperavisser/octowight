package nl.haploid.octowight.registry.data

trait ModelSerializer[M <: Model] {

  def serialize(model: M): String

  def deserialize(body: String, modelClass: Class[M]): M
}
