package malghafary;

import com.sun.javafx.beans.IDProperty;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@ToString(exclude = {"department"})
public class Employee {

    @Id
    private long id;

    private String firstName;
    private String lastName;

    @OneToOne(fetch = FetchType.EAGER)
    private ParkingSpace parkingSpace;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @Setter
    private Department department;


    // This is an element collection, since we are using List, we can set the @OrderBy
    @ElementCollection(fetch = FetchType.LAZY)
    @OrderBy()
    private List<String> names;

    public Employee(){
        this.names = new ArrayList<>();
    }

    public Employee(long id,String firstName,String lastName){
        this();
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void addName(String name){
        this.names.add(name);
    }

}