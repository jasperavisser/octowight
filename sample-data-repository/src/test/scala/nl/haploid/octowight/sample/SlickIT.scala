package nl.haploid.octowight.sample

import javax.sql.DataSource

import org.springframework.beans.factory.annotation.Autowired
import slick.driver.PostgresDriver.api._

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

class SlickIT extends AbstractIT {
  @Autowired private[this] val dataSource: DataSource = null

  class PersonTable(tag: Tag) extends Table[(Long, String)](tag, Some("octowight"), "person") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def name = column[String]("name")

    def * = (id, name)
  }

  val persons = TableQuery[PersonTable]

  class RoleTable(tag: Tag) extends Table[(Long, Long, String)](tag, Some("octowight"), "role") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def person = column[Long]("person")

    def name = column[String]("name")

    def * = (id, person, name)
  }

  val roles = TableQuery[RoleTable]

  "All roles" should "be listed" in {
    val database = Database.forDataSource(dataSource)
    try {

      database.run(roles +=(-666L, 7L, "cabin boy"))

      Await.result({

        val query = for {
          role <- roles
          person <- persons if person.id === role.person
        } yield (person.name, role.name)

        database.stream(query.result)
          .foreach(row => println(s"NOTE: ${row._1} is a ${row._2}"))

      }, Duration.Inf)


    } finally database.close()
  }
}
