package nl.haploid.octowight.registry.repository

import java.lang

import nl.haploid.octowight.registry.data.ResourceRoot
import org.apache.commons.lang3.builder.{EqualsBuilder, HashCodeBuilder}
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

import scala.beans.BeanProperty

object ResourceRootDmo {
  val IdSequence = "resourceId"
  val VersionSequence = "resourceVersion"

  def apply(resourceRoot: ResourceRoot) = {
    val resourceRootDmo = new ResourceRootDmo
    resourceRootDmo.setAtomId(resourceRoot.getAtomId)
    resourceRootDmo.setAtomOrigin(resourceRoot.getAtomOrigin)
    resourceRootDmo.setAtomType(resourceRoot.getAtomType)
    resourceRootDmo.setResourceId(resourceRoot.getResourceId)
    resourceRootDmo.setResourceType(resourceRoot.getResourceType)
    resourceRootDmo
  }
}

@Document(collection = "resourceRoot")
class ResourceRootDmo {

  @Id
  @BeanProperty var id: String = _
  @BeanProperty var resourceId: lang.Long = _
  @BeanProperty var resourceType: String = _
  @BeanProperty var atomId: lang.Long = _
  @BeanProperty var atomOrigin: String = _
  @BeanProperty var atomType: String = _
  @BeanProperty var version: lang.Long = _

  override def equals(that: Any) = EqualsBuilder.reflectionEquals(this, that, false)

  override def hashCode = HashCodeBuilder.reflectionHashCode(this, false)
}
