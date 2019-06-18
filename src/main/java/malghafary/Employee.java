package malghafary;

import com.sun.javafx.beans.IDProperty;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@ToString(exclude = {"department"})
public class Employee {

    @Id
    private long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @Setter
    private Department department;

    private String firstName;
    private String lastName;

    // This is an element collection, since we are using List, we can set the @OrderBy
    @ElementCollection
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
