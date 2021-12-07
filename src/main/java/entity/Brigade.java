package entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Brigade extends BaseEntity{
    private Person[] persons;
    private List<Flight> flights;
    private int iteration = 0;

    public Brigade() {
        persons = new Person[5];
        flights = new ArrayList<>();
    }

    public Person[] getPersons() {
        Arrays.sort(persons);
        return persons;
        //return Arrays.copyOf(persons, persons.length);
    }

    public void setPersons(Person[] persons) {
        if (this.persons.length == persons.length) {
            this.persons = persons;
        }
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    public void addPerson(Person person) {
        if (iteration != persons.length) {
            this.persons[iteration++] = person;
        }
    }

    public void addFlight(Flight flight) {
        this.flights.add(flight);
    }

    public int getIteration() {
        return iteration;
    }

    @Override
    public String toString() {
        return "Brigade{" +
                "id=" + getId() +
                ", persons=" + persons +
                ", flights=" + flights +
                '}';
    }
}
