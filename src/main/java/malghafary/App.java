package malghafary;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class App {
    public static void main(String[] args) {

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("testtest");
        EntityManager manager = factory.createEntityManager();

        /* 2 Types of collections when taking about mapping :
           Element collection (Embeddables,BasicTypes) : @ElementCollection
           Relationships : @ManyToOne, OneToMany
        */

        // Types Collection + Set has no special handling
        // Use @ElementCollection or @ManyToOne/@OneToMany/ManyToMany

        // List : supports ordering @OrderBy() -> by default will order by PK if an an entity collection
        //                                     -> Natural ordering if basic element collection
        //                                     -> No Ordering if Embeddables collection
        // In some cases you will not have an a column to use in the order, in the case you
        // can use the @OrderColumn annotation which will create a column for the order
        // so its either @OrderBy or @OrderColumn
        elementCollectionListOredered(manager);
        entityCollectionListOrdered(manager);

        /*
        Map : 9 combinations using 3 types (Basic,embeddables,entities)
        -- Skip this for now
         */

        /*
         * Collections mapping best practices:
         * When using a List, do not assume that it is ordered automatically if you have not actually
           specified any ordering. The List order might be affected by the database results, which are
           only partially deterministic with respect to how they are ordered. There are no guarantees that
           such an ordering will be the same across multiple executions

         * It will generally be possible to order the objects by one of their own attributes. Using the
           @OrderBy annotation will always be the best approach when compared to a persistent List
           that must maintain the order of the items within it by updating a specific order column. Use
           the order column only when it is impossible to do otherwise.

         * Map types are very helpful, but they can be relatively complicated to properly configure.
           Once you reach that stage, however, the modeling capabilities that they offer and the loose
           association support that can be leveraged makes them ideal candidates for various kinds of
           relationships and element collections.

         * As with the List, the preferred and most efficient use of a Map is to use an attribute of the target
           object as a key, making a Map of entities keyed by a basic attribute type the most common and
           useful. It will often solve most of the problems you encounter. A Map of basic keys and values
           can be a useful configuration for associating one basic object with another.

         * Avoid using embedded objects in a Map, particularly as keys, because their identity is typically
           not defined. Embeddables in general should be treated with care and used only when
           absolutely necessary.

         * Support for duplicate or null values in collections is not guaranteed, and is not recommended
           even when possible. They will cause certain types of operations on the collection type to
           be slower and more database-intensive, sometimes amounting to a combination of record
           deletion and insertion instead of simple updates.
         */


    }

    private static void entityCollectionListOrdered(EntityManager manager) {

        EntityTransaction transaction;
        transaction = manager.getTransaction();
        transaction.begin();

        // here we have a department entity, we will create 2 employees and add them to the same department
        // the employees are oredered by first name/last name after you get the Department entity from the
        // database, they will be oredered
        Department department = new Department(1);
        Employee employee1 = new Employee(12,"Mahmoud","Ahmad");
        employee1.setDepartment(department);
        Employee employee2 = new Employee(13,"Ahmad","Ahmad");
        employee2.setDepartment(department);

        manager.persist(department);
        manager.persist(employee1);
        manager.persist(employee2);

        transaction.commit();
        manager.refresh(department);

        department.getEmployees().forEach(e -> System.out.println(e));
    }

    private static void elementCollectionListOredered(EntityManager manager) {

        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();

        // Create an Employee Entity which has an element collection (names of type string)
        // Names are mapped as list and oredered
        Employee employee = new Employee(2l, "firstname", "lastname");
        employee.addName("B");
        employee.addName("C");
        employee.addName("A");

        // here we need to refresh the entity after the commit so that the values are
        // refreshed from the database
        manager.persist(employee);
        transaction.commit();
        manager.refresh(employee);

        //Get the employee and print the names, names will be sorted
        Employee anotherSession = manager.find(Employee.class,2l);
        anotherSession.getNames().forEach(s -> System.out.println(s));
    }
}
