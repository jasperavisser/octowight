package nl.haploid.octowight.sample.data;

import nl.haploid.octowight.JsonMapper;
import nl.haploid.octowight.registry.data.ModelSerializer;
import org.springframework.stereotype.Component;

@Component
public class CaptainModelSerializer implements ModelSerializer<CaptainModel> {

	@Override
	public String serialize(final CaptainModel model) {
		final JsonMapper jsonMapper = new JsonMapper();
		return jsonMapper.serialize(model);
	}

	@Override
	public CaptainModel deserialize(final String body, final Class<CaptainModel> modelClass) {
		final JsonMapper jsonMapper = new JsonMapper();
		return jsonMapper.deserialize(body, modelClass);
	}
}
