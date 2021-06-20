package pl.kielce.tu.neo4j.straz;

import org.neo4j.ogm.annotation.*;

import java.util.HashSet;
import java.util.Set;

@NodeEntity(label = "Firetruck")
public class Firetruck {

    @Id
    @GeneratedValue
    private Long id;

    @Property(name = "name")
    private String name;

    public Firetruck() {
    }

    public Firetruck(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Relationship(type = "FIREMAN_OPERATING")
    private Set<Fireman> firemen = new HashSet<>();

    public void addFireman(Fireman fireman) {
        firemen.add(fireman);
    }

    @Override
    public String toString() {
        return "Firetruck [id=" + id + ", name=" + name + "]";
    }
}
