package pl.kielce.tu.neo4j.straz;

import org.neo4j.ogm.annotation.*;

import java.util.HashSet;
import java.util.Set;

@NodeEntity(label = "Action")
public class Action {

    @Id
    @GeneratedValue
    private Long id;

    @Property(name = "location")
    private String location;

    public Action() {
    }

    public Action(String name) {
        this.location = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return location;
    }

    @Relationship(type = "ACTION_MEMBERS")
    private Set<Firetruck> firetrucks = new HashSet<>();

    public void addFiretruck(Firetruck firetruck) {
        firetrucks.add(firetruck);
    }

    @Override
    public String toString() {
        return "Action [id=" + id + ", location=" + location + "]";
    }
}
