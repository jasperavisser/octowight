package nl.haploid.octowight.sample.data;

import nl.haploid.octowight.JsonMapper;
import nl.haploid.octowight.registry.data.ModelSerializer;
import org.springframework.stereotype.Component;

@Component
public class CaptainModelSerializer implements ModelSerializer<CaptainModel> {

    @Override
    public String toString(final CaptainModel model) {
        final JsonMapper jsonMapper = new JsonMapper();
        return jsonMapper.toString(model);
    }
}
