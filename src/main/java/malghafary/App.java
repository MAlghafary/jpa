package malghafary;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class App 
{
    public static void main( String[] args ){

        /* Entity Manager Operations
         persist()      : accepts a new entity instance and causes it to become managed.
         contains()     : to check if an entity is already managed
         find()         : finds an entry by PK
         getReference() : Return a proxy to the entity, with only the PK
        */
        EntityManager manager = createEntityManager();
        //changesAreNotDirectlyPushedToDatabase(manager);
        inAssocationsNeedToUpdateTheOwningSideForChangesToBeReflected(manager);

        //System.out.println(dept.getEmployees());

        // find() : find an entity by PK
        //
        //Department secondDepartment = manager.getReference(Department.class,2l);
        //System.out.println(secondDepartment);



        //System.out.println(dept.getEmployees());

    }

    private static EntityManager createEntityManager() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("testtest");
        return factory.createEntityManager();
    }


    /*
    In Associations, you need to update the owning side for the association to be reflected on the database.
    Had we only added the employee to the collection and not updated the other side of
    the relationship, nothing would have been persisted to the database. You need to
    owning entity.
     */
    private static void inAssocationsNeedToUpdateTheOwningSideForChangesToBeReflected(EntityManager manager) {
        inAssocationsNeedToUpdateTheOwningSideForChangesToBeReflectedForOneToOne(manager);
        //inAssocationsNeedToUpdateTheOwningSideForChangesToBeReflectedManyToOne(manager);
    }

    private static void inAssocationsNeedToUpdateTheOwningSideForChangesToBeReflectedManyToOne(EntityManager manager) {
        EntityTransaction transaction = Utils.beginTransaction(manager);
        Department dept = manager.find(Department.class, 1l);
        Employee emp = new Employee();
        emp.setId(1);
        emp.setFirstName("Peter");
        //emp.setDepartment(dept);
        dept.getEmployees().add(emp);
        manager.persist(emp);
        manager.persist(dept);
        commitTransaction(transaction);
    }



    private static void inAssocationsNeedToUpdateTheOwningSideForChangesToBeReflectedForOneToOne(EntityManager manager) {
        EntityTransaction transaction = Utils.beginTransaction(manager);

        // OneToOne
        Employee employee = new Employee();
        employee.setFirstName("Employee 1");
        employee.setId(2);

        ParkingSpace parkingSpace = new ParkingSpace();
        parkingSpace.setId(1);
        parkingSpace.setLocation("Location Number 1");
        parkingSpace.setEmployee(employee);

        manager.persist(employee);
        manager.persist(parkingSpace);
        commitTransaction(transaction);
    }

    private static void commitTransaction(EntityTransaction transaction) {
        transaction.commit();
    }

    /*
      For an entity to be managed does not mean that it is persisted to the database right away.
      The actual SQL to create the necessary relational data will not be generated until the persistence
      context is synchronized with the database, typically only when the transaction commits.
     */
    private static void changesAreNotDirectlyPushedToDatabase(EntityManager manager) {

        EntityTransaction transaction = Utils.beginTransaction(manager);

        Clazz test = new Clazz();
        test.setName("Test Entity");
        manager.persist(test);// at this point the entity is not in the database yet
        commitTransaction(transaction);
    }
}
