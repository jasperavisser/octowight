package nl.haploid.octowight.registry.repository

import java.lang

import nl.haploid.octowight.registry.data.ResourceRoot
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

import scala.annotation.meta.field

object ResourceRootDmo {
  val IdSequence = "resourceId"
  val VersionSequence = "resourceVersion"

  def apply(resourceRoot: ResourceRoot) = {
    new ResourceRootDmo(
      root = AtomDmo(resourceRoot),
      resourceId = resourceRoot.resourceId,
      resourceCollection = resourceRoot.resourceCollection,
      tombstone = resourceRoot.tombstone,
      version = resourceRoot.version)
  }
}

@Document(collection = "resourceRoot")
case class ResourceRootDmo
(
  @(Id@field) id: String = null,
  resourceCollection: String,
  resourceId: lang.Long,
  root: AtomDmo,
  tombstone: Boolean = false,
  version: lang.Long
  )
