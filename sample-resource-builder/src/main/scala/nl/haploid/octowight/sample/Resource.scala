package nl.haploid.octowight.sample

import java.lang

import nl.haploid.octowight.registry.data.ResourceIdentifier

// TODO: move to resource-registry project (or cache project ...)
case class Resource(resourceIdentifier: ResourceIdentifier, model: String, tombstone: lang.Boolean)
