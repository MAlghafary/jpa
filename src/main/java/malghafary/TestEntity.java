package malghafary;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
public class TestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    // Mapping and element collection, same goes for the Embeddables
    // A Join table will be used to store this
    @ElementCollection
    private Collection<String> nickNames;

    // You can override using the @CollectionTable
    // in the case of Embeddables,you can also use @AttributeOverride
    @ElementCollection
    @CollectionTable(name = "entity_names",joinColumns = @JoinColumn(name = "EMP_ID"))
    private Collection<String> names;


}
