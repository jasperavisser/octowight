package nl.haploid.octowight.registry.data;

public interface ModelSerializer<T extends Model> {

    String toString(final T model);
}
