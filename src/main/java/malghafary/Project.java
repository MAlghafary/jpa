package malghafary;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
public class Project {
    @Id
    private long id;

    @ManyToMany(mappedBy = "projects")
    private Collection<Employee> employees;


}
