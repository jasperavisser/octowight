package nl.haploid.octowight.registry.repository

import java.io.Serializable
import java.lang

import nl.haploid.octowight.registry.data.ResourceIdentifier
import org.apache.commons.lang3.builder.{EqualsBuilder, HashCodeBuilder, ToStringBuilder}

import scala.beans.BeanProperty

object ResourceModelDmoId {

  def apply(resource: ResourceIdentifier) = {
    val resourceModelId = new ResourceModelDmoId
    resourceModelId.setResourceId(resource.getId)
    resourceModelId.setResourceType(resource.getType)
    resourceModelId
  }
}

class ResourceModelDmoId extends Serializable {
  @BeanProperty var resourceId: lang.Long = _
  @BeanProperty var resourceType: String = _

  override def equals(that: Any) = EqualsBuilder.reflectionEquals(this, that, false)

  override def hashCode = HashCodeBuilder.reflectionHashCode(this, false)

  override def toString = ToStringBuilder.reflectionToString(this)
}
