# INTRO

http://www.quora.com/Which-one-is-better-for-durable-messaging-with-good-query-features-RabbitMQ-or-Kafka

# INTRO

TODO: http://www.scalatest.org/user_guide/using_the_scalatest_maven_plugin

## Choices

- Do we use Scala over Java?
  - PRO: More compact
  - PRO:
  - CON: Harder to debug
  - CON: Much less documentation on the internet
  - CON: Doesn't work well with jmockit
  - CON: scalatest integration with maven; hard to split unit/IT
  - CON: scalatest integration with IDE/jenkins
  - CON: scalatest ... is just not junit yet
  - CON: Less knowledge inside PV
  - CON: end result looks like java anyway :(
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
- Spring integration?
  - PRO: It's a framework
  - CON: Too complicated for simple use case
  - CON: Hard to ensure good error handling

## Akka

atomChangeEventPollActor
- case PollEvent: eventRepository.findAll.asScala.map(x => eventLogActor ? PublishEvent(x))

EventLogActor
- case PublishEvent(x): producer.produce("eventLog", x)
If produce fails, then event is lost. :( Even if we use a DurableMailbox. We could stash message, catch exception, unstash message, die.

## Assumptions

Each resource has a single row that represents its main component
Order of row change events (dirty rows?) does not matter
It suffices to send 3rd parties a stream of dirty resources

## New resource detection

Consume events
For each event
    If it is not the main component of an existing resource (simple lookup on KVS)
        Query to see if it represents a new resource
    (each row type might be different types of resources, e.g. role == patient OR role == practitioner)
    (can do this in batches for events of the same row type, so group events by row type)
Publish new resources (isDirty, hasType(PATIENT), hasMainRow("Role", 12345L))

## Dirty resource detection

Consume events
For each event
    For each resource R of which it is a component (simple lookup on KVS)
        Publish R.isDirty

## Resource API

On GET patient/{id}
    If dirty
        Get main component id from KVS
        Query for components
        Check if it is still a resource (could be in the same query)
        Store components C1..Cn of resource R (in KVS)
        Store in cache
    Else
        Fetch from cache
