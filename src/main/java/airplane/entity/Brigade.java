package airplane.entity;

import com.google.gson.annotations.Expose;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Brigade extends Entity {
    public static final byte DEFAULT_SIZE_OF_BRIGADE = 5;
    @Expose
    private List<Person> persons;
    @Expose
    private List<Flight> flights;

    public Brigade() {
        persons = new ArrayList<>();
        flights = new ArrayList<>();
    }

    public void setPersons(List<Person> persons) {
        if (persons != null) {
            this.persons = persons;
            Collections.sort(this.persons);
        }
    }

    public List<Person> getPersons() {
        return persons;
    }

    public List<Flight> getFlights() {
        return flights;
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
