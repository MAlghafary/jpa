Requirements for an Entity class :
1- Must be annotated with @Entity annotation or declared in the xml
2- Must have an Id
3- Must have a public/protected no-args constructor,the class may have other constructors
4- A top level class(abstract,concrete but not an enum nor an interface)
5- Not final,no final persistence methods or fields
6- No inheritance constraints,Entity class can extend Entity/none-entity
   none-entity can extend Entity
7- In some cases, the Entity class must implement the interface Serializable
-- This is the JPA specification,hibernate might be different
>> BadEntity.java
-------------------------------------------------
3 types of access : Field/Property/Mixed
  Field access :
    -> you annotate the fields
    -> Field access ( reflection)
    -> Getters and setters are ignored
    -> Don't do Pubic
    -> All non-transient instance variables that are not annotated with the Transient annotation are persistent.
    >> see FieldAccess.java
  Property access:
    -> you annotate the getters
    -> should follow JavaBean naming convention
    -> Both methods must be either public/protected
    -> All properties not annotated with the Transient annotation are persistent.

    >> See PropertyAccess.java
  Mixed access:
    -> possible to have field and property access within the same entity hierarchy, or in same entity.
    -> The behavior of applications that mix the placement of
      annotations on fields and properties within an entity hierarchy without explicitly specifying the
      Access annotation is undefined.
    -> Explicitly set access type using @Access annotation
    -> override
    -> mark field/property as @Transient
    >> see MixedAccess.java
>> Field access is the way to go most of the time
----------------------------------------------------------------------
>> SimpleEntity.java
>> Employee.java + Department.java + ParkingSpace.java + Project.java
