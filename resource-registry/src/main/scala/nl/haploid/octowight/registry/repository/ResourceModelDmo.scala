package nl.haploid.octowight.registry.repository

import java.lang

import nl.haploid.octowight.registry.data.Resource
import org.apache.commons.lang3.builder.{EqualsBuilder, HashCodeBuilder, ToStringBuilder}
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

import scala.beans.BeanProperty

object ResourceModelDmo {

  def apply(resource: Resource[_], body: String) = {
    val resourceModelDmo = new ResourceModelDmo
    resourceModelDmo.setId(ResourceModelDmoId(resource))
    resourceModelDmo.setBody(body)
    resourceModelDmo.setVersion(resource.getVersion)
    resourceModelDmo
  }
}

@Document(collection = "resourceModel")
class ResourceModelDmo {

  @Id
  @BeanProperty var id: ResourceModelDmoId = _
  @BeanProperty var version: lang.Long = _
  @BeanProperty var body: String = _

  override def equals(that: Any) = EqualsBuilder.reflectionEquals(this, that, false)

  override def hashCode = HashCodeBuilder.reflectionHashCode(this, false)

  override def toString = ToStringBuilder.reflectionToString(this)
}
