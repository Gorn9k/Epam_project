package entity;

public class Flight extends Entity {
    private Brigade brigade;
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
    public String toString() {
        return "Flight{" +
                "id=" + getId() +
                ", flightName='" + flightName + '\'' +
                '}';
    }
}
