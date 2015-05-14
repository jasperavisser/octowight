package nl.haploid.octowight.registry.repository

import java.lang

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

import scala.beans.BeanProperty

@Document(collection = "sequence")
class SequenceDmo {

  @Id
  @BeanProperty var key: String = _
  @BeanProperty var value: lang.Long = _
}