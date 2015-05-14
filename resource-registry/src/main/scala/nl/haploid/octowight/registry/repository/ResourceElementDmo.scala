package nl.haploid.octowight.registry.repository

import java.lang

import nl.haploid.octowight.registry.data.{Atom, Resource}
import org.apache.commons.lang3.builder.{EqualsBuilder, HashCodeBuilder}
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

import scala.beans.BeanProperty

object ResourceElementDmo {

  def apply(resource: Resource[_], atom: Atom) = {
    val dmo = new ResourceElementDmo
    dmo.setAtomId(atom.getAtomId)
    dmo.setAtomOrigin(atom.getAtomOrigin)
    dmo.setAtomType(atom.getAtomType)
    dmo.setResourceId(resource.getId)
    dmo.setResourceType(resource.getType)
    dmo
  }
}

@Document(collection = "resourceElement")
class ResourceElementDmo {

  @Id
  @BeanProperty var id: String = _
  @BeanProperty var resourceId: lang.Long = _
  @BeanProperty var resourceType: String = _
  @BeanProperty var atomId: lang.Long = _
  @BeanProperty var atomOrigin: String = _
  @BeanProperty var atomType: String = _

  override def equals(that: Any) = EqualsBuilder.reflectionEquals(this, that, false)

  override def hashCode = HashCodeBuilder.reflectionHashCode(this, false)
}
