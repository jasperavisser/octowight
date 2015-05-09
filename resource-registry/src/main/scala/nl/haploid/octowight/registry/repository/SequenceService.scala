package nl.haploid.octowight.registry.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.query.{Criteria, Query, Update}
import org.springframework.data.mongodb.core.{FindAndModifyOptions, MongoOperations}
import org.springframework.stereotype.Service

// TODO: test
@Service
class SequenceService {
  @Autowired private val mongoOperations: MongoOperations = null

  def getNextValue(key: String) = {
    val sequence = incrementSequence(key)
    if (sequence != null) {
      sequence.getValue
    } else {
      startSequence(key).getValue
    }
  }

  protected def startSequence(key: String) = {
    val sequence = new Sequence
    sequence.setKey(key)
    sequence.setValue(0l)
    mongoOperations.save(sequence)
    sequence
  }

  protected def incrementSequence(key: String) = {
    val query = new Query(Criteria.where("_id").is(key))
    val update = new Update().inc("value", 1)
    val options = new FindAndModifyOptions().returnNew(true)
    mongoOperations.findAndModify(query, update, options, classOf[Sequence])
  }
}
