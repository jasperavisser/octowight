package nl.haploid.octowight.registry.data;

import java.util.Collection;

public interface Resource<T extends Model> {

    Collection<Atom> getAtoms();

    long getId();

    String getType();

    T getModel();

    Long getVersion();
}
