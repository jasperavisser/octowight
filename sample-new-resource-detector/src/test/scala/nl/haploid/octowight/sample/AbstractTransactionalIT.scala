package nl.haploid.octowight.sample

import org.scalatest.OneInstancePerTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.DefaultTransactionDefinition

abstract class AbstractTransactionalIT extends AbstractIT with OneInstancePerTest {
  @Autowired private[this] val platformTransactionManager: PlatformTransactionManager = null

  private[this] lazy val transactionStatus = platformTransactionManager.getTransaction(new DefaultTransactionDefinition)

  override def beforeEach() = {
    super.beforeEach()
    transactionStatus.setRollbackOnly()
  }

  override def afterEach() = platformTransactionManager.rollback(transactionStatus)
}
