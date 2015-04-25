package nl.haploid.octowight.registry.data;

public interface ModelSerializer<M extends Model> {

	String serialize(final M model);

	M deserialize(final String body, final Class<M> modelClass);
}
