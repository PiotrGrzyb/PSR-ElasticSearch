package pl.kielce.tu.neo4j.straz;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

@NodeEntity(label = "Fireman")
public class Fireman {

    @Id
    @GeneratedValue
    private Long id;

    @Property(name = "name")
    private String name;

    public Fireman() {
    }

    public Fireman(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Fireman [id=" + id + ", name=" + name + "]";
    }
}
