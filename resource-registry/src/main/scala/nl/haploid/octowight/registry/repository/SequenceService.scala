package nl.haploid.octowight.registry.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.query.{Criteria, Query, Update}
import org.springframework.data.mongodb.core.{FindAndModifyOptions, MongoOperations}
import org.springframework.stereotype.Service

@Service
class SequenceService {
  @Autowired private[this] val mongoOperations: MongoOperations = null

  def nextValue(key: String): Long = incrementSequence(key).getOrElse(startSequence(key)).value

  protected def startSequence(key: String): SequenceDmo = {
    val sequenceDmo = new SequenceDmo(key = key, value = 0L)
    mongoOperations.save(sequenceDmo)
    sequenceDmo
  }

  protected def incrementSequence(key: String): Option[SequenceDmo] = {
    val query = new Query(Criteria.where("_id").is(key))
    val update = new Update().inc("value", 1)
    val options = new FindAndModifyOptions().returnNew(true)
    Option(mongoOperations.findAndModify(query, update, options, classOf[SequenceDmo]))
  }
}
