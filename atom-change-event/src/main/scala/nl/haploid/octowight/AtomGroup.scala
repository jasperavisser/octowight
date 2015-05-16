package nl.haploid.octowight

import org.apache.commons.lang3.builder.{EqualsBuilder, HashCodeBuilder, ToStringBuilder}

import scala.beans.BeanProperty

object AtomGroup {

  def apply(origin: String, category: String): AtomGroup = {
    val g = new AtomGroup
    g.setCategory(category)
    g.setOrigin(origin)
    g
  }
}

class AtomGroup {
  @BeanProperty var origin: String = _
  @BeanProperty var category: String = _

  override def equals(that: Any) = EqualsBuilder.reflectionEquals(this, that, false)

  override def hashCode = HashCodeBuilder.reflectionHashCode(this, false)

  override def toString = ToStringBuilder.reflectionToString(this)
}
