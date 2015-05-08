package nl.haploid.octowight.registry.repository

import java.io.Serializable
import java.lang

import org.apache.commons.lang3.builder.{EqualsBuilder, HashCodeBuilder}

import scala.beans.BeanProperty

class ResourceModelId extends Serializable {

  @BeanProperty var resourceId: lang.Long = _
  @BeanProperty var resourceType: String = _

  override def equals(that: Any) = EqualsBuilder.reflectionEquals(this, that, false)

  override def hashCode = HashCodeBuilder.reflectionHashCode(this, false)
}
