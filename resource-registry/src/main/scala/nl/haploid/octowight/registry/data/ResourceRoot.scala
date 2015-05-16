package nl.haploid.octowight.registry.data

import java.lang

import nl.haploid.octowight.AtomChangeEvent
import nl.haploid.octowight.registry.repository.ResourceRootDmo
import org.apache.commons.lang3.builder.{EqualsBuilder, HashCodeBuilder, ToStringBuilder}

import scala.beans.BeanProperty

object ResourceRoot {

  def apply(event: AtomChangeEvent, resourceType: String) = {
    val r = new ResourceRoot
    r.setAtomId(event.getAtomId)
    r.setAtomOrigin(event.getAtomOrigin)
    r.setAtomCategory(event.getAtomCategory)
    r.setResourceType(resourceType)
    r
  }

  def apply(resourceRootDmo: ResourceRootDmo) = {
    val r = new ResourceRoot
    r.setAtomId(resourceRootDmo.getRoot.getId)
    r.setAtomOrigin(resourceRootDmo.getRoot.getOrigin)
    r.setAtomCategory(resourceRootDmo.getRoot.getCategory)
    r.setResourceId(resourceRootDmo.getResourceId)
    r.setResourceType(resourceRootDmo.getResourceType)
    r.setTombstone(resourceRootDmo.getTombstone)
    r.setVersion(resourceRootDmo.getVersion)
    r
  }
}

class ResourceRoot extends ResourceIdentifier with Atomizable {

  @BeanProperty var resourceId: lang.Long = _
  @BeanProperty var resourceType: String = _
  // TODO: use Atom, also Type->Category
  @BeanProperty var atomId: lang.Long = _
  @BeanProperty var atomOrigin: String = _
  @BeanProperty var atomCategory: String = _
  @BeanProperty var tombstone: Boolean = false
  @BeanProperty var version: lang.Long = _

  override def getId: lang.Long = resourceId

  override def getType: String = resourceType

  def key = s"$getAtomOrigin:$getAtomCategory/$getAtomId->$getResourceType"

  override def equals(that: Any) = EqualsBuilder.reflectionEquals(this, that, false)

  override def hashCode = HashCodeBuilder.reflectionHashCode(this, false)

  override def toString = ToStringBuilder.reflectionToString(this)

  override def toAtom: Atom = new Atom(getAtomId, getAtomOrigin, getAtomCategory)
}
