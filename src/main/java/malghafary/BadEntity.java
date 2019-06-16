package malghafary;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

// If the annotation is missing, you will get unkonwnEntity exception
@Entity
@NoArgsConstructor
@Data
public class BadEntity {

    @Id // if this missing,you will get exception
    private int id;
    private String field1;
    private String field2;


    public BadEntity(int id, String field1, String field2) {
        this.id = id;
        this.field1 = field1;
        this.field2 = field2;
    }

    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("testtest");
        EntityManager manager = factory.createEntityManager();

        // Assuming such entity exists in the DB, this will through
        // the exception :
        // org.hibernate.InstantiationException: No default constructor for entity
        // if there is no no-args constructor
        // manager.persist will work OK
        BadEntity entity = manager.find(BadEntity.class,1);
        System.out.println(entity);


    }
}
