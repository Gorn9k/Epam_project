package entity;

import java.util.UUID;

public class Person {
    private UUID id;
    private String name;
    private PersonType personType;

    public Person(String name, PersonType personType){
        id = UUID.randomUUID();
        this.name = name;
        this.personType = personType;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PersonType getPersonType() {
        return personType;
    }

    public void setPersonType(PersonType personType) {
        this.personType = personType;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", personType=" + personType +
                '}';
    }
}
