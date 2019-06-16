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


    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("testtest");
        EntityManager manager = factory.createEntityManager();

        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();

        Clazz test = new Clazz();
        test.setName("jpa test");

        Clazz test2 = new Clazz();
        test2.setName("jpa test2");

        manager.persist(test);
        manager.persist(test2);
        transaction.commit();
    }
}
