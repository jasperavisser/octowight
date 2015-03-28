package nl.haploid.adapter

import javax.sql.DataSource

import grizzled.slf4j.Logging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class SomeService @Autowired()(dataSource: DataSource) extends Logging {

  @Scheduled(fixedDelay = 1000)
  def doSomething() = {
    warn("Something happened")
    // TODO: Read from table
    // TODO: Write to kafka
    // TODO: Delete from table
  }

  def getConnection =
    dataSource.getConnection
}
