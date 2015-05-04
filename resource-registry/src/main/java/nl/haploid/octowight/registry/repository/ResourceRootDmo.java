package nl.haploid.octowight.registry.repository;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;

@Entity
@Table(name = "resource_root", schema = "octowight")
public class ResourceRootDmo {

    @Id
    @SequenceGenerator(name = "resource_root_sequence", sequenceName = "octowight.resource_root_sequence")
    @GeneratedValue(generator = "resource_root_sequence")
    private Long id;

    @Column(name = "resource_id")
    @Generated(value = GenerationTime.INSERT)
    private Long resourceId;

    @Column(name = "resource_type")
    private String resourceType;

    @Column(name = "atom_id")
    private Long atomId;

    @Column(name = "atom_locus")
    private String atomLocus;

    @Column(name = "atom_type")
    private String atomType;

    @Column
    @Generated(value = GenerationTime.INSERT)
    private Long version;

    public Long getAtomId() {
        return atomId;
    }

    public void setAtomId(final Long atomId) {
        this.atomId = atomId;
    }

    public String getAtomLocus() {
        return atomLocus;
    }

    public void setAtomLocus(final String atomLocus) {
        this.atomLocus = atomLocus;
    }

    public String getAtomType() {
        return atomType;
    }

    public void setAtomType(final String atomType) {
        this.atomType = atomType;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(final Long version) {
        this.version = version;
    }

    @Override
    public boolean equals(final Object that) {
        return EqualsBuilder.reflectionEquals(this, that, false);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, false);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
