package nl.haploid.octowight.registry.repository

import java.lang

import nl.haploid.octowight.registry.data.ResourceRoot
import org.apache.commons.lang3.builder.{EqualsBuilder, HashCodeBuilder, ToStringBuilder}
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

import scala.beans.BeanProperty

object ResourceRootDmo {
  val IdSequence = "resourceId"
  val VersionSequence = "resourceVersion"

  def apply(resourceRoot: ResourceRoot) = {
    val d = new ResourceRootDmo
    d.setRoot(AtomDmo(resourceRoot))
    d.setResourceId(resourceRoot.getResourceId)
    d.setResourceType(resourceRoot.getResourceType)
    d.setTombstone(resourceRoot.getTombstone)
    d.setVersion(resourceRoot.getVersion)
    d
  }
}

@Document(collection = "resourceRoot")
class ResourceRootDmo {

  @Id
  @BeanProperty var id: String = _
  // TODO: resource id + type must be unique (index?)
  @BeanProperty var resourceId: lang.Long = _
  @BeanProperty var resourceType: String = _
  @BeanProperty var root: AtomDmo = _
  @BeanProperty var tombstone: Boolean = false
  @BeanProperty var version: lang.Long = _

  override def equals(that: Any) = EqualsBuilder.reflectionEquals(this, that, false)

  override def hashCode = HashCodeBuilder.reflectionHashCode(this, false)

  override def toString = ToStringBuilder.reflectionToString(this)
}
