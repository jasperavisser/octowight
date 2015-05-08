package nl.haploid.octowight.registry.repository

import java.lang

import org.apache.commons.lang3.builder.{EqualsBuilder, HashCodeBuilder}
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

import scala.beans.BeanProperty

@Document(collection = "resourceElement") class ResourceElementDmo {

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
