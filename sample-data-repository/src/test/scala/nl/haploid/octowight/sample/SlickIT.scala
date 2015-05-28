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
    def id = column[Long]("id", O.PrimaryKey)

    def name = column[String]("name")

    def * = (id, name)
  }
  
  class RoleTable(tag: Tag) extends Table[(Long, Long, String)](tag, Some("octowight"), "role") {
    def id = column[Long]("id", O.PrimaryKey)

    def person = column[Long]("person")

    def name = column[String]("name")

    def personForeignKey = foreignKey("", person, persons)(_.id)

    def * = (id, person, name)
  }

  val roles = TableQuery[RoleTable]
  val persons = TableQuery[PersonTable]

  "All roles" should "be listed" in {
    val database = Database.forDataSource(dataSource)
    try {

      Await.result({
        val query = for {
          role <- roles
          person <- persons
        } yield (person.name, role.name)

        database.stream(query.result).foreach(r => println(s"NOTE: ${r._1} is a ${r._2}"))
      }, Duration.Inf)


    } finally database.close()
  }
}
