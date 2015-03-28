# INTRO

TODO: http://www.scalatest.org/user_guide/using_the_scalatest_maven_plugin

## Choices

- Do we use Scala over Java?
  - PRO: More compact
  - PRO:
  - CON: Harder to debug
  - CON: Much less documentation on the internet
  - CON: Doesn't work well with jmockit
  - CON: Less knowledge inside PV
  - CON: 
- Do we use jOOQ over Hibernate?
  - PRO: Easy to read query syntax
  - PRO: Better performance on JOIN queries
  - PRO:
  - CON: More difficult DMO modelling
  - CON: Less knowledge inside PV
  - CON: 
- Do we use Play or Akka over Spring?
  - PRO: Better integration with Scala
  - PRO: Reactive programming is cool
  - CON: Less knowledge inside PV
  
## Akka

RowChangeEventPollActor
- case PollEvent: eventRepository.findAll.asScala.map(x => eventLogActor ? PublishEvent(x))

EventLogActor
- case PublishEvent(x): producer.produce("eventLog", x)
If produce fails, then event is lost. :( Even if we use a DurableMailbox. We could stash message, catch exception, unstash message, die.
