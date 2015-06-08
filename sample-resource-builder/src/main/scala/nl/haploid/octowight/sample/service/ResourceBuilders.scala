package nl.haploid.octowight.sample.service

import java.util

import nl.haploid.octowight.sample.builder.ResourceBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import scala.collection.JavaConverters._

@Component
class ResourceBuilders {
  @Autowired private[this] val resourceBuilders: util.List[ResourceBuilder] = null

  private[this] lazy val resourceBuildersByCollection: Map[String, Iterable[ResourceBuilder]] =
    resourceBuilders.asScala.groupBy(_.collection)

  def forCollection(collection: String): Iterable[ResourceBuilder] =
    resourceBuildersByCollection.getOrElse(collection, {
      throw new scala.RuntimeException(s"No resource builders found for collection $collection")
    })
}
