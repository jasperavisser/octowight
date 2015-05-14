package nl.haploid.octowight.sample

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.support.DefaultTransactionDefinition
import org.springframework.transaction.{PlatformTransactionManager, TransactionStatus}

abstract class AbstractTransactionalIT extends AbstractIT {
  @Autowired private[this] val platformTransactionManager: PlatformTransactionManager = null

  private[this] lazy val transactionStatus: TransactionStatus = {
    platformTransactionManager.getTransaction(new DefaultTransactionDefinition)
  }

  override def beforeEach() = {
    super.beforeEach()
    transactionStatus.setRollbackOnly()
  }

  override def afterEach() = {
    platformTransactionManager.rollback(transactionStatus)
  }
}
