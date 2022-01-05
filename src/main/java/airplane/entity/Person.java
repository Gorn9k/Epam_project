package airplane.entity;

import com.google.gson.annotations.Expose;
import java.util.List;
import java.util.Objects;

public class Person extends Entity implements Comparable<Person>{
    @Expose
    private String personName;
    @Expose
    private PersonType personType;
    @Expose
    private boolean isFree = true;
    @Expose(serialize = false)
    private List<Flight> flights;

    public Person(){}

    public Person(Long id, String personName, PersonType personType, boolean isFree) {
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

    public List<Flight> getFlights() {
        return flights;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return isFree == person.isFree && Objects.equals(personName, person.personName) && personType == person.personType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(personName, personType, isFree);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + getId() +
                ", personName='" + personName + '\'' +
                ", personType=" + personType +
                ", isFree=" + isFree +
                '}';
    }

    @Override
    public int compareTo(Person o) {
        return this.getPersonType().compareTo(o.getPersonType());
    }
}
