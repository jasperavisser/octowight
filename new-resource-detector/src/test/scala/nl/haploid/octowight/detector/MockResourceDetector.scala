package nl.haploid.octowight.detector

import java.util
import java.util.Collections

import nl.haploid.octowight.AtomChangeEvent
import nl.haploid.octowight.registry.data.ResourceRootFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import scala.collection.JavaConverters._

object MockResourceDetector {
  val AtomType = "john"
  val ResourceType = "preston"
}

@Component
class MockResourceDetector extends ResourceDetector {
  @Autowired private[this] val resourceRootFactory: ResourceRootFactory = null

  def getAtomTypes = Collections.singletonList(MockResourceDetector.AtomType)

  def detect(events: util.List[AtomChangeEvent]) = {
    events.asScala
      .map(resourceRootFactory.fromAtomChangeEvent(_, MockResourceDetector.ResourceType))
      .toList
      .asJava
  }
}
