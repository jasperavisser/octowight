package nl.haploid.octowight.registry.data

import java.lang

import scala.beans.BeanProperty

class ResourceRoot {

  @BeanProperty var resourceId: lang.Long = _
  @BeanProperty var resourceType: String = _
  @BeanProperty var atomId: lang.Long = _
  @BeanProperty var atomOrigin: String = _
  @BeanProperty var atomType: String = _
  @BeanProperty var version: lang.Long = _

  def key = s"$getAtomOrigin:$getAtomType/$getAtomId->$getResourceType"
}
