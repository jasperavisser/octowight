package nl.haploid.octowight.registry.repository

import java.lang

import nl.haploid.octowight.registry.data.{Atom, Resource}
import org.apache.commons.lang3.builder.{EqualsBuilder, HashCodeBuilder, ToStringBuilder}
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

import scala.beans.BeanProperty

object ResourceElementDmo {

  def apply(resource: Resource[_], atom: Atom) = {
    val d = new ResourceElementDmo
    d.setAtomId(atom.id)
    d.setAtomOrigin(atom.origin)
    d.setAtomType(atom.typ)
    d.setResourceId(resource.getId)
    d.setResourceType(resource.getType)
    d
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

  override def toString = ToStringBuilder.reflectionToString(this)
}
