package malghafary;

import javax.persistence.*;

@Entity
@Access(AccessType.FIELD)
public class MixedAccess {
    @Id
    private int id;
    private String name;
    @Transient private String lastName;

    public MixedAccess() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Access(AccessType.PROPERTY)
    public String getLastName() {
        return lastName + "++";

    }

    @Access(AccessType.PROPERTY)
    public void setLastName(String lastName) {
        this.lastName = lastName.substring(2);
    }

    public static void main(String[] args) {

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("testtest");
        EntityManager manager = factory.createEntityManager();

        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();
        MixedAccess mixedAccess = new MixedAccess();
        mixedAccess.setId(1);
        mixedAccess.setName("Name");
        mixedAccess.setLastName("LastName");
        manager.persist(mixedAccess);
        transaction.commit();



    }
}
