package nl.haploid.octowight.registry.data

import java.lang

import nl.haploid.octowight.AtomChangeEvent
import nl.haploid.octowight.registry.repository.ResourceRootDmo
import org.apache.commons.lang3.builder.{EqualsBuilder, HashCodeBuilder, ToStringBuilder}

import scala.beans.BeanProperty

object ResourceRoot {

  def apply(event: AtomChangeEvent, resourceType: String) = {
    val r = new ResourceRoot
    r.setRoot(new Atom(event.getAtomId, event.getAtomOrigin, event.getAtomCategory))
    r.setResourceType(resourceType)
    r
  }

  def apply(resourceRootDmo: ResourceRootDmo) = {
    val r = new ResourceRoot
    r.setRoot(new Atom(resourceRootDmo.getRoot.getId, resourceRootDmo.getRoot.getOrigin, resourceRootDmo.getRoot.getCategory))
    r.setResourceId(resourceRootDmo.getResourceId)
    r.setResourceType(resourceRootDmo.getResourceType)
    r.setTombstone(resourceRootDmo.getTombstone)
    r.setVersion(resourceRootDmo.getVersion)
    r
  }
}

// TODO: consider making most data objects immutable
class ResourceRoot extends ResourceIdentifier with Atomizable {
  @BeanProperty var resourceId: lang.Long = _
  @BeanProperty var resourceType: String = _
  @BeanProperty var root: Atom = _
  @BeanProperty var tombstone: Boolean = false
  @BeanProperty var version: lang.Long = _

  override def getId: lang.Long = resourceId

  override def getType: String = resourceType

  def key = s"${root.origin}:${root.category}/${root.id}->$resourceType"

  override def equals(that: Any) = EqualsBuilder.reflectionEquals(this, that, false)

  override def hashCode = HashCodeBuilder.reflectionHashCode(this, false)

  override def toString = ToStringBuilder.reflectionToString(this)

  override def toAtom: Atom = new Atom(getRoot.id, getRoot.origin, getRoot.category)
}
