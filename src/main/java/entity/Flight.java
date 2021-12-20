package entity;

import com.google.gson.annotations.Expose;

import java.util.Objects;

public class Flight extends Entity {
    private Brigade brigade;
    @Expose
    private String flightName;

    public Flight(){}

    public Flight(Brigade brigade, String flightName) {
        this.brigade = brigade;
        this.flightName = flightName;
    }

    public Brigade getBrigade() {
        return brigade;
    }

    public void setBrigade(Brigade brigade) {
        this.brigade = brigade;
    }

    public String getFlightName() {
        return flightName;
    }

    public void setFlightName(String flightName) {
        this.flightName = flightName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Flight flight = (Flight) o;
        return Objects.equals(brigade, flight.brigade) && Objects.equals(flightName, flight.flightName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), brigade, flightName);
    }

    @Override
    public String toString() {
        return "Flight{" +
                "id=" + getId() +
                ", flightName='" + flightName + '\'' +
                //", brigade='" + brigade + '\'' +
                '}';
    }
}
