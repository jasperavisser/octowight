package nl.haploid.octowight.registry.repository

import java.io.Serializable
import java.lang

import nl.haploid.octowight.registry.data.ResourceIdentifier
import org.apache.commons.lang3.builder.{ToStringBuilder, EqualsBuilder, HashCodeBuilder}

import scala.beans.BeanProperty

object ResourceModelId {

  def apply(resource: ResourceIdentifier) = {
    val resourceModelId = new ResourceModelId
    resourceModelId.setResourceId(resource.getId)
    resourceModelId.setResourceType(resource.getType)
    resourceModelId
  }
}

class ResourceModelId extends Serializable {
  @BeanProperty var resourceId: lang.Long = _
  @BeanProperty var resourceType: String = _

  override def equals(that: Any) = EqualsBuilder.reflectionEquals(this, that, false)

  override def hashCode = HashCodeBuilder.reflectionHashCode(this, false)

  override def toString = ToStringBuilder.reflectionToString(this)
}
