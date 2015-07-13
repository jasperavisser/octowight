package nl.haploid.octowight.registry.repository

import java.lang

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

import scala.annotation.meta.field

@Document(collection = "resourceModel")
case class ResourceModelDmo
(
  @(Id@field) id: ResourceModelDmoId,
  version: lang.Long,
  body: String)
