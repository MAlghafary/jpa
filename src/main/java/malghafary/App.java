package malghafary;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("testtest");
        EntityManager manager = factory.createEntityManager();

        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();

        Clazz test = new Clazz();
        test.setName("jpa test");
        manager.persist(test);
        transaction.commit();
    }
}
