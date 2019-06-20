package malghafary;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Department {

    @Id
    private long id;

    private String name;
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


    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public List<Employee> getEmployees() {
        return this.employees;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Department)) return false;
        final Department other = (Department) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.getId() != other.getId()) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$employees = this.getEmployees();
        final Object other$employees = other.getEmployees();
        if (this$employees == null ? other$employees != null : !this$employees.equals(other$employees)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Department;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final long $id = this.getId();
        result = result * PRIME + (int) ($id >>> 32 ^ $id);
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $employees = this.getEmployees();
        result = result * PRIME + ($employees == null ? 43 : $employees.hashCode());
        return result;
    }

    public String toString() {
        return "Department(id=" + this.getId() + ", name=" + this.getName() + ", employees=" + this.getEmployees() + ")";
    }
}