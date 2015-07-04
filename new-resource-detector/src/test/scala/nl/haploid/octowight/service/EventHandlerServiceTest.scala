package nl.haploid.octowight.service

import java.util.concurrent.Future

import nl.haploid.octowight._
import nl.haploid.octowight.registry.data.ResourceRoot
import nl.haploid.octowight.registry.service.ResourceRegistryService
import org.apache.kafka.clients.producer.RecordMetadata

class EventHandlerServiceTest extends AbstractTest {
  @Tested private val eventHandlerService: EventHandlerService = null
  @Mocked private[this] val eventConsumerService: EventConsumerService = null
  @Mocked private[this] val resourceDetectorsService: ResourceDetectorsService = null
  @Mocked private[this] val resourceRegistryService: ResourceRegistryService = null
  @Mocked private[this] val dirtyResourceProducerService: DirtyResourceProducerService = null

  behavior of "Event handler service"

  it should "detect new resources" in {
    val topic = TestData.nextString
    val origin = TestData.nextString
    val category1 = TestData.nextString
    val category2 = TestData.nextString
    val event1 = TestData.atomChangeEvent(origin, category1)
    val event2 = TestData.atomChangeEvent(origin, category1)
    val event3 = TestData.atomChangeEvent(origin, category2)
    val events: Set[AtomChangeEvent] = Set(event1, event2, event3)
    val resourceRoot1: ResourceRoot = TestData.resourceRoot(TestData.nextLong)
    val resourceRoot2: ResourceRoot = TestData.resourceRoot(TestData.nextLong)
    val resourceRoots = List(resourceRoot1, resourceRoot2)
    val future1 = mock[Future[RecordMetadata]]
    val future2 = mock[Future[RecordMetadata]]
    val recordMetadata1 = new RecordMetadata(null, 1, 1)
    val recordMetadata2 = new RecordMetadata(null, 2, 2)
    expecting {
      eventConsumerService.topic andReturn topic once()
      eventConsumerService.consumeDistinctEvents() andReturn events once()
      resourceDetectorsService.detectResources(new AtomGroup(origin = origin, category = category1), Set(event1, event2)) andReturn resourceRoots once()
      resourceDetectorsService.detectResources(new AtomGroup(origin = origin, category = category2), Set(event3)) andReturn List() once()
      resourceRegistryService.saveResource(resourceRoot1) andReturn Some(resourceRoot1) once()
      resourceRegistryService.saveResource(resourceRoot2) andReturn Some(resourceRoot2) once()
      dirtyResourceProducerService.sendDirtyResource(resourceRoot1) andReturn future1 once()
      dirtyResourceProducerService.sendDirtyResource(resourceRoot2) andReturn future2 once()
      dirtyResourceProducerService.resolveFuture(future1) andReturn recordMetadata1 once()
      dirtyResourceProducerService.resolveFuture(future2) andReturn recordMetadata2 once()
      eventConsumerService.commit()
    }
    whenExecuting(eventConsumerService, resourceDetectorsService, resourceRegistryService, dirtyResourceProducerService) {
      val actualCount = eventHandlerService.detectNewResources(10)
      actualCount should be(2)
    }
  }
}
