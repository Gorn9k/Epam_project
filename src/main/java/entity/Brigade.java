package entity;

public class Brigade extends BaseEntity{
    private String dateFormation;
    private Person[] persons;
    private Flight[] flights;

    public String getDateFormation() {
        return dateFormation;
    }

    public void setDateFormation(String dateFormation) {
        this.dateFormation = dateFormation;
    }

    public Person[] getPersons() {
        return persons;
    }

    public void setPersons(Person[] persons) {
        this.persons = persons;
    }

    public Flight[] getFlights() {
        return flights;
    }

    public void setFlights(Flight[] flights) {
        this.flights = flights;
    }
}
