package nl.haploid.octowight.registry.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class SequenceService {

	@Autowired
	private MongoOperations mongoOperations;

	public long getNextValue(final String key) {
		final Sequence sequence = incrementSequence(key);
		if (sequence != null) {
			return sequence.getValue();
		}
		return startSequence(key).getValue();
	}

	protected Sequence startSequence(final String key) {
		final Sequence sequence = new Sequence();
		sequence.setKey(key);
		sequence.setValue(0l);
		mongoOperations.save(sequence);
		return sequence;
	}

	protected Sequence incrementSequence(final String key) {
		final Query query = new Query(Criteria.where("_id").is(key));
		final Update update = new Update().inc("value", 1);
		final FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true);
		return mongoOperations.findAndModify(query, update, options, Sequence.class);
	}
}
