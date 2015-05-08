package nl.haploid.octowight.registry.repository

import java.lang

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

import scala.beans.BeanProperty

@Document(collection = "resourceModel") class ResourceModelDmo {

  @Id
  @BeanProperty var id: ResourceModelId = _
  @BeanProperty var version: lang.Long = _
  @BeanProperty var body: String = _
}
