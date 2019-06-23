package malghafary;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.stream.Stream;

public class App 
{
    public static void main( String[] args ){

        // JPA provides the Query and TypedQuery interfaces to configure and execute queries. The Query interface is used in
        //cases when the result type is Object or in dynamic queries when the result type may not be known ahead of time.
        //The TypedQuery interface is the preferred one and can be used whenever the result type is known. As TypedQuery
        //extends Query, a strongly typed query can always be treated as an untyped version, though not vice versa. An
        //implementation of the appropriate interface for a given query is obtained through one of the factory methods in the
        //EntityManager interface. The choice of factory method depends on the type of query (JP QL, SQL, or criteria object),
        //whether the query has been predefined, and whether strongly typed results are desired. For now, we will restrict our
        //discussion to JP QL queries. SQL query definition is discussed in Chapter 11, and criteria queries are discussed
        //in Chapter 9.
        //There are three approaches to defining a JP QL query. A query may either be dynamically specified at runtime,
        //configured in persistence unit metadata (annotation or XML) and referenced by name, or dynamically specified and
        //saved to be later referenced by name. Dynamic JP QL queries are nothing more than strings, and therefore may be
        //defined on the fly as the need arises. Named queries, on the other hand, are static and unchangeable, but are more
        //efficient to execute because the persistence provider can translate the JP QL string to SQL once when the application
        //starts as opposed to every time the query is executed. Dynamically defining a query and then naming it allows a
        //dynamic query to be reused multiple times throughout the life of the application but incur the dynamic processing
        //cost only once.

        /*
        2 points for using JPQL :
        1- portability
        2- wiring queries against the domain model

        4 Types of queries : select,aggregate,update,and delete
         */

        /*
         SELECT
         <select_expression>
         FROM <from_clause>
         [WHERE <conditional_expression>]
         [ORDER BY <order_by_clause>]
         - Alias for entity is mandatory (unlike SQL)
         */

        selectACollectionOfEmployees();
        selectUsingPathExpressions();
        selectUsingInvalidIdentificationVariable();



        // Every query must have at least one identification
        //variable defined in the FROM clause, and that variable must correspond to an entity type
        // Id variable name must follow the java variables naming rules
        selectUsingInvalidIdentificationVariable();

        /*
        Joins occur whenever any of the following conditions are met in a select query.
        1. Two or more range variable declarations are listed in the FROM clause and appear in the
        select clause.
        2. The JOIN operator is used to extend an identification variable using a path expression.
        3. A path expression anywhere in the query navigates across an association field, to the same
        or a different entity.
        4. One or more WHERE conditions compare attributes of different identification variables.
        */
        // Path navigation uses inner joins
        /*
        SELECT p
        FROM Employee e
        JOIN e.phones p  >> a path expression with alias


        SELECT p.number
        FROM Employee e
        JOIN e.phones p

        // the below 2 quires are the same :
        1-
        SELECT d
        FROM Employee e
        JOIN e.department d

        2-
        SELECT e.department
        FROM Employee e

         */

        /*
        Join Conditions in the WHERE Clause : mostly used if there is no relationship in the domain model
        SELECT d, m
        FROM Department d, Employee m
        WHERE d = m.department AND
        m.directs IS NOT EMPTY
         */

        /*
        Multiple Joins :
        SELECT DISTINCT p
        FROM Department d
        JOIN d.employees e
        JOIN e.projects p
         */

        // Map Joins :
        /*
        SELECT e.name, p
        FROM Employee e
        JOIN e.phones p
        >> identification variables based on maps refer to the value by default.
        >> To access the key, you need to use KEY:

        SELECT e.name, KEY(p), VALUE(p)
        FROM Employee e JOIN e.phones p
        WHERE KEY(p) IN ('Work', 'Cell')

        Finally, in the event that we want both the key and the value returned together in the form of a
        java.util.Map.Entry object, we can specify the ENTRY keyword in the same fashion. Note that the ENTRY
        keyword can only be used in the SELECT clause. The KEY and VALUE keywords can also be used as part of
        conditional expressions in the WHERE and HAVING clauses of the query.
         */

        // outer join

        // SELECT e, d
        // FROM Employee e
        // LEFT JOIN e.department d   >> will return employees with and without departments

        // JPQL :
        // SELECT e, d
        //FROM Employee e LEFT JOIN e.department d
        // SQL :
        // SELECT e.id, e.name, e.salary, e.manager_id, e.dept_id, e.address_id,
        //d.id, d.name
        //FROM employee e LEFT OUTER JOIN department d
        //ON (d.id = e.department_id)

        // JPQL:
        // SELECT e, d
        //FROM Employee e LEFT JOIN e.department d
        //ON d.name LIKE 'QA%'

        // SQL:
        // SELECT e.id, e.name, e.salary, e.department_id, e.manager_id, e.address_id,
        //d.id, d.name
        //FROM employee e left outer join department d
        //ON ((d.id = e.department_id) and (d.name like 'QA%'))

        // note that the result will be diffrent than this : ( this is like inner join)
        // SELECT e, d
        //FROM Employee e LEFT JOIN e.department d
        //WHERE d.name LIKE 'QA%'


        // Fetch Joins
        // Fetch joins are intended to help application designers optimize their database access and prepare query results for
        //detachment. They allow queries to specify one or more relationships that should be navigated and prefetched by the
        //query engine so that they are not lazy loaded later at runtime.
        //For example, if we have an Employee entity with a lazy loading relationship to its address, the following query can
        //be used to indicate that the relationship should be resolved eagerly during query execution:

        // SELECT e
        // FROM Employee e
        // JOIN FETCH e.address

        // Note that no identification variable is set for the e.address path expression. This is because even though
        //the Address entity is being joined in order to resolve the relationship, it is not part of the result type of the query.


        // The result of executing the query is still a collection of Employee entity instances, except that the address relationship
        //on each entity will not cause a secondary trip to the database when it is accessed. This also allows the address
        //relationship to be accessed safely if the Employee entity becomes detached. A fetch join is distinguished from a regular
        //join by adding the FETCH keyword to the JOIN operator.

        // Fetch join and duplicate results
        // SELECT d
        //FROM Department d LEFT JOIN FETCH d.employees
        //WHERE d.deptno = 1

        // "A fetch join has the same join semantics as the corresponding inner or outer join, except that the
        // related objects specified on the right-hand side of the join operation are not returned in the query
        // result or otherwise referenced in the query.  Hence, for example, if department 1 has five employees,
        // the above query returns five references to the department 1 entity."

        // WHERE Clause
        // - 2 types of parameters :
        // 1- Ordinal

        // public Country getCountryByName(EntityManager em, String name) {
        //    TypedQuery<Country> query = em.createQuery(
        //        "SELECT c FROM Country c WHERE c.name = ?1", Country.class);
        //    return query.setParameter(1, name).getSingleResult();
        //  }


        // 2- Named
        //  public Country getCountryByName(EntityManager em, String name) {
        //    TypedQuery<Country> query = em.createQuery(
        //        "SELECT c FROM Country c WHERE c.name = :name", Country.class);
        //    return query.setParameter("name", name).getSingleResult();
        //  }


        // Where Expressions :
        // 1- BETWEEN :
        // SELECT e
        //FROM Employee e
        //WHERE e.salary BETWEEN 40000 AND 45000

        // 2- LIKE : _ for single char match , % for multichar match
        //SELECT d
        //FROM Department d
        //WHERE d.name LIKE '__Eng%'

        // 3- Subqueries
        // SELECT e   >> will select the employee with max salary
        //FROM Employee e
        //WHERE e.salary = (SELECT MAX(emp.salary)
        //FROM Employee emp)

        // SELECT e  >> This query returns all the employees who have a cell phone number
        //FROM Employee e
        //WHERE EXISTS (SELECT 1
        //FROM Phone p
        //WHERE p.employee = e AND p.type = 'Cell')

        // This query returns all the employees who have a cell phone number. This is also an example of a subquery that
        //returns a collection of values. The EXISTS expression in this example returns true if any results are returned by the
        //subquery. Returning the literal 1 from the subquery is a standard practice with EXISTS expressions because the actual
        //results selected by the subquery do not matter; only the number of results is relevant. Note that the WHERE clause
        //of the subquery references the identifier variable e from the main query and uses it to filter the subquery results.
        //Conceptually, the subquery can be thought of as executing once for each employee. In practice, many database
        //servers will optimize these types of queries into joins or inline views in order to maximize performance.

        // 4- IN Expression
        // SELECT e
        //FROM Employee e
        //WHERE e.address.state IN ('NY', 'CA')

        // 5- Collection Expressions
        // IS EMPTY / IS NOT EMPTY
        // MEMBER OF / NOT MEMBER OF

        // 6-  EXISTS
        // 7-  ANY, ALL, SOME
        // 8-  LITERALS : https://www.logicbig.com/tutorials/java-ee-tutorial/jpa/jpql-literals.html
        // 9-  String functions : https://www.logicbig.com/tutorials/java-ee-tutorial/jpa/jpql-string-functions.html
        // 10- Arithmetic functions : https://www.logicbig.com/tutorials/java-ee-tutorial/jpa/jpql-arithmetic-functions.html
        // 11- DateTime Functions : https://www.logicbig.com/tutorials/java-ee-tutorial/jpa/jpql-date-time-functions.html
        // 12- DataBase Function : https://www.logicbig.com/tutorials/java-ee-tutorial/jpa/jpql-calling-database-functions.html
        // 13- CASE Expression : https://www.logicbig.com/tutorials/java-ee-tutorial/jpa/jpql-case-expressions.html
        // 14 - Order by : https://www.logicbig.com/tutorials/java-ee-tutorial/jpa/order-by-clause.html
        // 15- Aggreigate functions : https://www.logicbig.com/tutorials/java-ee-tutorial/jpa/jpql-aggregate-functions.html
        // 16- Group by and having : https://www.logicbig.com/tutorials/java-ee-tutorial/jpa/jpql-group-by-having.html
        // 17- Update : https://www.logicbig.com/tutorials/java-ee-tutorial/jpa/update-queries.html
        // 18- Delete : https://www.logicbig.com/tutorials/java-ee-tutorial/jpa/jpql-delete-queries.html
        // 19- Polymorphic Queries : https://www.logicbig.com/tutorials/java-ee-tutorial/jpa/jpql-polymorphic-queries.html
        // 20- DownCasting : https://www.logicbig.com/tutorials/java-ee-tutorial/jpa/jpql-downcasting-with-treat-operator.html





















    }

    private static void selectACollectionOfEmployees() {
        EntityManager em = Utils.createEntityManager();
        String query = "select e from Employee e";
        List<Employee> result = em.createQuery(query).getResultList();
        result.forEach(e -> {
            System.out.println(e.getFirstName());
        });
        // for eager relationships the SQL statement would either be extended to
        // retrieve the extra data or multiple sql statements would have been
        // executed

        // For performance tuning in particular, understanding how your vendor approaches
        // SQL generation can help you write more efficient queries
    }

    private static void selectUsingPathExpressions() {
        /*
          Path expressions are used to navigate from an entity,3 options:
          1- Entity attribute
          2- Single-valued association
          3- Collection-valued association

          - Important : A path cannot continue from a state field or collection-valued association.
         */
        //selectUsingPathExpressionsForEntityAttributes();
        //selectUsingPathExpressionsForEntityRelationships();
        /*
        The thing to remember about selecting embeddables is that the returned objects will not be managed.
        If you issue a query to return employees (select e FROM Employee e) and then from the results navigate to their
        ContactInfo embedded objects, you would be obtaining embeddables that were managed. Changes to any one
        of those objects would be saved when the transaction committed. Changing any of the ContactInfo object results
        returned from a query that selected the ContactInfo directly, however, would have no persistent effect
         */
    }

    private static void selectUsingInvalidIdentificationVariable() {
        String query = "select 1e from Employee 1e";
        List<Employee> resultList = Utils.createEntityManager().createQuery(query).getResultList();
        System.out.println(resultList);
    }

    private static void selectUsingPathExpressionsForEntityAttributes() {
        // find names of all employees
        // the result type is the type of the attribute (firstName) so
        // in this case it will be a collection of strings
        EntityManager em = Utils.createEntityManager();
        String query = "select e.firstName from Employee e";
        List<String> result = em.createQuery(query).getResultList();
        result.forEach(Utils::print);

        //select multiple attributes,in this case the result will be a collection of Object[]
        query = "select e.firstName,e.lastName from Employee  e";
        List<Object[]> result2 = em.createQuery(query).getResultList();
        result2.forEach(e -> {
            System.out.println(e[0]);
            System.out.println(e[1]);
        });
        // for multiple attributes you can also use the Constructor Expressions
        query = "select NEW malghafary.EmployeeDetails(e.firstName,e.lastName) from Employee  e";
        List<EmployeeDetails> employeeDetails = em.createQuery(query).getResultList();
        employeeDetails.forEach(System.out::println);
    }

    private static void selectUsingPathExpressionsForEntityRelationships() {
        EntityManager em = Utils.createEntityManager();
        // select all departments of employees
        // the result will be og type List<Department> // Stream<Department>
        String query = "select e.department from Employee e";
        Stream<Department> result = em.createQuery(query).getResultStream();
        result.forEach(System.out::println);

        //select departments removing duplicates
        query = "select distinct e.department from Employee  e";
        result = em.createQuery(query).getResultStream();
        result.forEach(System.out::println);

        //selecting collection relationships is illegal,works in hibernate OK
        query = "select d.employees from Department d";
        List invalidResult= em.createQuery(query).getResultList();
        for (Object o : invalidResult) {
            System.out.println(o);
        }

        //navegating from a collection relationship is illegal
        query = "select d.employees.firstName from Department d";
        invalidResult= em.createQuery(query).getResultList();
        for (Object o : invalidResult) {
            System.out.println(o);
        }

    }
}
