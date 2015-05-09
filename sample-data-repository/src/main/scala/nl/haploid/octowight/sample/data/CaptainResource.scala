package nl.haploid.octowight.sample.data

import java.{lang, util}

import nl.haploid.octowight.registry.data.Resource
import nl.haploid.octowight.sample.repository.{PersonDmo, RoleDmo}

import scala.beans.BeanProperty

object CaptainResource {
  val ResourceType: String = "captain"
}

class CaptainResource extends Resource[CaptainModel] {
  private[this] var personDmo: PersonDmo = null
  private[this] var roleDmo: RoleDmo = null
  @BeanProperty var id: lang.Long = null

  def getAtoms = util.Arrays.asList(personDmo, roleDmo)

  def setPersonDmo(personDmo: PersonDmo) = this.personDmo = personDmo

  def setRoleDmo(roleDmo: RoleDmo) = this.roleDmo = roleDmo

  def getType = CaptainResource.ResourceType

  def getModel = {
    val captainModel = new CaptainModel
    captainModel.setId(personDmo.getId)
    captainModel.setName(personDmo.getName)
    captainModel
  }
}
