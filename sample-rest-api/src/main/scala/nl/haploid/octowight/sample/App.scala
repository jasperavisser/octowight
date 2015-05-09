package nl.haploid.octowight.sample

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.{DataSourceAutoConfiguration, DataSourceTransactionManagerAutoConfiguration}
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration
import org.springframework.context.annotation.ComponentScan

// TODO: are the excludes still needed?
@ComponentScan(basePackages = Array("nl.haploid.octowight"))
@EnableAutoConfiguration(exclude = Array(classOf[DataSourceAutoConfiguration], classOf[HibernateJpaAutoConfiguration], classOf[JpaRepositoriesAutoConfiguration], classOf[DataSourceTransactionManagerAutoConfiguration]))
object App {
  def main(args: Array[String]): Unit = SpringApplication.run(classOf[App])
}

class App
