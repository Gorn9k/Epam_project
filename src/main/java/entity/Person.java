package entity;

import java.util.UUID;

public class Person extends BaseEntity{
    private Brigade brigade;
    private PersonType personType;
    private String personName;
    private String birthday;
    private String tel;
    private String address;

    public Person(String personName, PersonType personType){
        super();
        this.personName = personName;
        this.personType = personType;
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
                "personType=" + personType +
                ", personName='" + personName + '\'' +
                ", birthday='" + birthday + '\'' +
                ", tel='" + tel + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
