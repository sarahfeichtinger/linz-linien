package at.htl.linzlinien.entity;

import javax.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "Location.findByName",
                query = "select l from Location l where l.name like :NAME"
        )
})
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "LO_NAME", unique = true)
    private String name;

    //region constructors
    public Location() {
    }

    public Location(String name) {
        this.setName(name);
    }
    //endregion

    //region getter and setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    //endregion

    @Override
    public String toString() {
        return String.format("%d: %s", getId(), getName());
    }
}
