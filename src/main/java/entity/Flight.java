package entity;

public class Flight extends BaseEntity{
    private Brigade brigade;
    private String flightName;

    public Flight(String flightName, Brigade brigade){
        super();
        this.flightName = flightName;
        this.brigade = brigade;
    }

    public Brigade getBrigade() {
        return brigade;
    }

    public void setBrigade(Brigade brigade) {
        this.brigade = brigade;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "brigade=" + brigade +
                ", flightName='" + flightName + '\'' +
                '}';
    }
}
