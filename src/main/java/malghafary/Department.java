package malghafary;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
public class Department {

    @Id
    private long id;

    // specifying the order by columns for the List
    @OneToMany(mappedBy = "department")
    @OrderBy("firstName asc ,lastName asc")
    private List<Employee> employees;

    public Department() {
        this.employees = new ArrayList<>();
    }

    public Department(long id) {
        this();
        this.id = id;
    }

    public void addEmployee(Employee employee){
        this.employees.add(employee);
    }




}
