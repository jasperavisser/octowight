package nl.haploid.octowight.registry.repository

import java.lang

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

import scala.annotation.meta.field

@Document(collection = "sequence")
case class SequenceDmo
(
  @(Id@field) key: String,
  value: lang.Long)
