package malghafary;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Clazz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;
}
