package entity;

public class Flight {
    private String name;
    Brigade brigade;

    public Flight(String name, Brigade brigade){
        this.name = name;
        this.brigade = brigade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
                "name='" + name + '\'' +
                ", brigade=" + brigade +
                '}';
    }
}
