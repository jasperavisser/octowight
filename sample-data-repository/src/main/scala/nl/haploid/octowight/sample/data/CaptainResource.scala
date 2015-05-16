package nl.haploid.octowight.sample.data

import java.lang

import nl.haploid.octowight.registry.data.Resource
import nl.haploid.octowight.sample.repository.{PersonDmo, RoleDmo}

object CaptainResource {
  val ResourceCollection: String = "captain"
}

case class CaptainResource
(
  id: lang.Long,
  version: lang.Long,
  personDmo: PersonDmo,
  roleDmo: RoleDmo
  ) extends Resource[CaptainModel] {

  override def atoms = List(personDmo.toAtom, roleDmo.toAtom)

  override def collection = CaptainResource.ResourceCollection

  override def model = new CaptainModel(id = personDmo.getId, name = personDmo.getName)
}
