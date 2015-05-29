package nl.haploid.octowight.sample

import javax.sql.DataSource

import nl.haploid.octowight.sample.repository.AtomChangeEventTable
import nl.haploid.octowight.service.EventChannelService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.scheduling.annotation.Scheduled
import slick.driver.PostgresDriver.api._

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

object App {
  def main(args: Array[String]): Unit = SpringApplication.run(classOf[App])
}

@EnableAutoConfiguration
@ComponentScan(basePackages = Array("nl.haploid.octowight"))
class App {
  @Autowired private[this] val eventChannelService: EventChannelService = null
  @Autowired private[this] val dataSource: DataSource = null

  lazy val database = Database.forDataSource(dataSource)

  val atomChangeEvents = TableQuery[AtomChangeEventTable]

  @Scheduled(fixedRate = 1000)
  def poll(): Unit = {
    val query = for (events <- atomChangeEvents.result) yield events
    val events = Await.result(database.run(query), Duration.Inf)
      .map(_.toAtomChangeEvent)
    eventChannelService.sendEvents(events)
    val ids = events.map(_.id)
    val delete = for (_ <- atomChangeEvents.filter(event => ids.contains(event.id)).delete) yield ()
    Await.result(database.run(delete), Duration.Inf)
  }
}
