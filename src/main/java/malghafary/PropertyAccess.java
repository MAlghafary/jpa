package malghafary;

import javax.persistence.*;

@Entity
public class PropertyAccess {
    private int id;
    private String name;

    public PropertyAccess() {
    }

    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        System.out.println("Set Id is called");
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        System.out.println("Set Name is called");
        this.name = name;
    }

    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("testtest");
        EntityManager manager = factory.createEntityManager();

        PropertyAccess propertyAccess = manager.find(PropertyAccess.class,1);
        System.out.println(propertyAccess);
    }
    }

