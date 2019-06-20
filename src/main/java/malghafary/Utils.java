package malghafary;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class Utils {

    public static EntityTransaction beginTransaction(EntityManager manager) {
        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();
        return transaction;
    }
}
