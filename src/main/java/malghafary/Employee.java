package malghafary;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;


//   Properties of relationships:
//   1- Direction   : bidirectional/unidirectional
//   2- Cardinality : one to one, one to many, many to many
//   3- Ordinality  : optionality

//   In almost every relationship,one of the sides will have the join column
//   in the table,this table is called the owning side, the other table is called
//   none-owning side, annotations are always placed on the owning side of the

//   Many to one : Many employees work in the same department (@ManytoOne on the employee entity )
//   One to One  : One employee has one parking space (@OneToOne on the employee entity ) or
//   the other wat round, both are OK.
//   One to many : A department has a number of employees (@OneToOne(mappedBy="department")

//  Important : Failing to specify the mappedBy element in the @OneToMany annotation will cause the provider to treat it as a
//  unidirectional one-to-many relationship that is defined to use a join table (described later). This is an easy mistake to
//  make and should be the first thing you look for if you see a missing table error with a name that has two entity names
//  concatenated together.

//  Many to Many : Employees can work on multiple projects:@ManyToMany on both sides,choose one as
//  owner, there is no actual owner since a join table will be used in this case

//  You can set the fetch type on the relationships annotations @OneToOne(fetch=FetchType.LAZY)

// Embedded Object: Has a separate java class, but saved in the same database record
// Annotate with @Embeddable so that it will be persisted as part of the entity row, in the entity, you need to use @Embedded
@Entity
@Data
@NoArgsConstructor
public class Employee {

    @Id
    private long id;

    // This is how you map the many to one, this is the owning side
    @ManyToOne(cascade = CascadeType.ALL)
    private Department department;
    // Override the join column name
    @ManyToOne
    @JoinColumn(name = "DEP_ID")
    private Department anotherDepartment;

    @OneToOne(cascade = CascadeType.ALL)
    private ParkingSpace parkingSpace;

    //Here is a many to many relationship,using the JoinTable annotation
    @ManyToMany
    @JoinTable(name="EMP_PROJ",
            joinColumns=@JoinColumn(name="EMP_ID"),
            inverseJoinColumns=@JoinColumn(name="PROJ_ID"))
    private Collection<Project> projects;

    @Embedded
    private Address address;


    private String firstName;
    private String lastName;


    public static void main(String[] args) {

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("testtest");
        EntityManager manager = factory.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();
        Employee employee = new Employee();
        employee.setId(1);
        employee.setFirstName("FirstName");
        employee.setLastName("LastName");
        employee.setAddress(Address.builder().city("city").state("state").street("street").build());

        ParkingSpace parkingSpace = new ParkingSpace();
        parkingSpace.setId(1);
        parkingSpace.setLocation("Parking Space Location");

        employee.setParkingSpace(parkingSpace);

        Department department = new Department();
        department.setId(1);
        department.setName("Department");

        employee.setDepartment(department);

        manager.persist(employee);
        transaction.commit();
    }
}
