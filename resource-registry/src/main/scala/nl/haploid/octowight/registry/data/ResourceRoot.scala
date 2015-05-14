package nl.haploid.octowight.registry.data

import java.lang

import nl.haploid.octowight.AtomChangeEvent
import nl.haploid.octowight.registry.repository.ResourceRootDmo
import org.apache.commons.lang3.builder.{EqualsBuilder, HashCodeBuilder, ToStringBuilder}

import scala.beans.BeanProperty

object ResourceRoot {

  def apply(event: AtomChangeEvent, resourceType: String) = {
    val resourceRoot = new ResourceRoot
    resourceRoot.setAtomId(event.getAtomId)
    resourceRoot.setAtomOrigin(event.getAtomOrigin)
    resourceRoot.setAtomType(event.getAtomType)
    resourceRoot.setResourceType(resourceType)
    resourceRoot
  }

  def apply(resourceRootDmo: ResourceRootDmo) = {
    val resourceRoot = new ResourceRoot
    resourceRoot.setAtomId(resourceRootDmo.getAtomId)
    resourceRoot.setAtomOrigin(resourceRootDmo.getAtomOrigin)
    resourceRoot.setAtomType(resourceRootDmo.getAtomType)
    resourceRoot.setResourceId(resourceRootDmo.getResourceId)
    resourceRoot.setResourceType(resourceRootDmo.getResourceType)
    resourceRoot.setVersion(resourceRootDmo.getVersion)
    resourceRoot
  }
}

class ResourceRoot extends ResourceIdentifier {

  @BeanProperty var resourceId: lang.Long = _
  @BeanProperty var resourceType: String = _
  @BeanProperty var atomId: lang.Long = _
  @BeanProperty var atomOrigin: String = _
  @BeanProperty var atomType: String = _
  @BeanProperty var version: lang.Long = _

  override def getId: lang.Long = resourceId

  override def getType: String = resourceType

  def key = s"$getAtomOrigin:$getAtomType/$getAtomId->$getResourceType"

  override def equals(that: Any) = EqualsBuilder.reflectionEquals(this, that, false)

  override def hashCode = HashCodeBuilder.reflectionHashCode(this, false)

  override def toString = ToStringBuilder.reflectionToString(this)
}
