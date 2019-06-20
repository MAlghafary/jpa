package malghafary;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@Data
@NoArgsConstructor

public class ParkingSpace {

    @Id
    private long id;

    private String location;

    // Inverse of the relationship, the name is the name of the attribute in the employee entity
    @OneToOne(mappedBy = "parkingSpace")
    private Employee employee;
}