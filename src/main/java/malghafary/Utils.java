package malghafary;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Utils {

    public static EntityManager createEntityManager() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("testtest");
        return factory.createEntityManager();
    }

    public static EntityTransaction beginTransaction(EntityManager manager) {
        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();
        return transaction;
    }

    public static void commitTransaction(EntityTransaction transaction) {
        transaction.commit();
    }

    public static void print(Object object){
        System.out.println(object);
    }
}
