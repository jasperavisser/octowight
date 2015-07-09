package nl.haploid.octowight.sample.repository

import java.lang

import nl.haploid.octowight.registry.data.ResourceMessage
import nl.haploid.octowight.sample.data.CaptainModel
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

import scala.annotation.meta.field

@Document(collection = "captain")
case class CaptainDmo(
                       @(Id@field) id: lang.Long = null,
                       model: CaptainModel = null,
                       tombstone: Boolean = false
                       )
