package malghafary;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;


// - The bare minimum needed to map an entity is @Entity and @Id
// - The default table name is the class name, you can override using @Table(name="")
// - You can also provide a catalog which has meaning in some databases
// - Specify meta-data for the column using the @Column annotation
// - Lazy fetching with @Basic(fetch = FetchType.LAZY)
// - Lazy fetching to be avoided for simple types most of the time, it makes much more sense
//   with relationships mapping
// - To map CLOB and BLOB columns, you need to add @Lob annotation
// - Mapping enums : Ordinal by default,can override with @Enumerated(EnumType.STRING)
// - Temporal types : SQL/Java, SQL : java.sql.Date, java.sql.Time, and java.sql.Timestamp
//                              JAVA : java.util.Date,java.util.Calendar
// - When using Temporal java types, you need to set @Temporal
// - You can use transient or @Transient to skip persistence for a field
// - PK Generation strategies : AUTO, TABLE, SEQUENCE, or IDENTITY
//   @Id @GeneratedValue(strategy=AUTO)
//   AUTO  : JPA Provider will pick(for development only(
//   TABLE : a table is used to store the ids
//   SEQUENCE : supported by some databases,internal method to generate ids
//   IDENTITY : supported by some databases, the ids will be generated after the record is inserted
@Entity
@Table(name = "SIP",schema = "DIP")
@Data
@NoArgsConstructor
// You can remove most of this, the provider will use defaults
@TableGenerator(name="Address_Gen",
        table="ID_GEN",
        pkColumnName="GEN_NAME",
        valueColumnName="GEN_VAL",
        pkColumnValue="Addr_Gen",
        initialValue=10000,
        allocationSize=100)
public class SimpleEntity {

    @Id
    @GeneratedValue(generator="Address_Gen")
    private int id;

    @Column(name = "THIS_NAME")
    private String name;

    @Column
    @Basic(fetch = FetchType.LAZY)
    private String comments;

    @Lob
    @Column
    private byte[] image;


    private Title title;

    @Enumerated(EnumType.STRING)
    private Type type;

    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Temporal(TemporalType.TIME)
    private Date endDate;

    public static void main(String[] args) {
        {
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("testtest");
            EntityManager manager = factory.createEntityManager();
            EntityTransaction transaction = manager.getTransaction();
            transaction.begin();
            SimpleEntity simpleEntity = new SimpleEntity();
            simpleEntity.setId(1);
            simpleEntity.setName("Simple Entity");
            simpleEntity.setTitle(Title.MR);
            simpleEntity.setType(Type.First);
            simpleEntity.setStartDate(new Date());
            simpleEntity.setEndDate(new Date());
            manager.persist(simpleEntity);
            transaction.commit();
        }

    }
}

enum Title {
    MR,MS,MRS
}

enum Type {
    First,Second,Third
}
