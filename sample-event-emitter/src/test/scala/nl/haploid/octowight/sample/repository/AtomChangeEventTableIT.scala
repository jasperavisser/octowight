package nl.haploid.octowight.sample.repository

import nl.haploid.octowight.sample.{AbstractIT, TestData}
import slick.driver.PostgresDriver.api._

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

class AtomChangeEventTableIT extends AbstractIT {
  val atomChangeEvents = TableQuery[AtomChangeEventTable]

  "Atom change event repository" should "find all events" in {
    val event = TestData.atomChangeEventDmo

    val actions = (for {
      _ <- atomChangeEvents.delete
      _ <- atomChangeEvents += event
      events <- atomChangeEvents.result
      _ <- SimpleDBIO[Unit](_.connection.rollback())
    } yield events).transactionally

    val events = Await.result(database.run(actions), Duration.Inf)

    events should have size 1
  }
}
