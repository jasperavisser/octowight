package nl.haploid.resource.detector.repository;

import javax.persistence.*;

@Entity
@Table(name = "huxtable", schema = "octowight")
public class Huxtable {

    @Id
    @SequenceGenerator(name = "huxtable_sequence", schema = "octowight")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "type")
    private String type;

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }
}
