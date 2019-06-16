package malghafary;


import javax.persistence.*;
import java.lang.reflect.Field;

@Entity
public class FieldAccess {
    @Id
    private int id;
    // this field has no annotation, by default this will persisted
    public String name;

    public FieldAccess() {
    }

    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("testtest");
        EntityManager manager = factory.createEntityManager();

        FieldAccess fieldAccess = manager.find(FieldAccess.class,10);
        System.out.println(fieldAccess);
    }
}
