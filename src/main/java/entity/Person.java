package entity;

import java.util.List;

public class Person extends BaseEntity implements Comparable<Person>{
    private String personName;
    private PersonType personType;
    private boolean isFree = true;
    private List<Flight> flights;

    public Person(){}

    public Person(String personName, PersonType personType) {
        this.personName = personName;
        this.personType = personType;
    }

    public Person(Integer id, String personName, PersonType personType, boolean isFree) {
        super(id);
        this.personName = personName;
        this.personType = personType;
        this.isFree = isFree;
    }

    public Person(String personName, PersonType personType, boolean isFree) {
        this.personName = personName;
        this.personType = personType;
        this.isFree = isFree;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public PersonType getPersonType() {
        return personType;
    }

    public void setPersonType(PersonType personType) {
        this.personType = personType;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + getId() +
                ", personName='" + personName + '\'' +
                ", personType=" + personType +
                ", isFree=" + isFree +
                ", flights=" + flights +
                '}';
    }

    @Override
    public int compareTo(Person o) {
        return this.getPersonType().compareTo(o.getPersonType());
    }
}
