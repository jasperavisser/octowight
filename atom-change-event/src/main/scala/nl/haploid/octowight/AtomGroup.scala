package nl.haploid.octowight

import org.apache.commons.lang3.builder.{ToStringBuilder, EqualsBuilder, HashCodeBuilder}

import scala.beans.BeanProperty

class AtomGroup {
  @BeanProperty var atomOrigin: String = _
  @BeanProperty var atomType: String = _

  override def equals(that: Any) = EqualsBuilder.reflectionEquals(this, that, false)

  override def hashCode = HashCodeBuilder.reflectionHashCode(this, false)

  override def toString = ToStringBuilder.reflectionToString(this)
}
