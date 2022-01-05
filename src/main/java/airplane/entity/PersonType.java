package airplane.entity;

public enum PersonType {
    PILOT("PILOT"), NAVIGATOR("NAVIGATOR"), RADIOMAN("RADIOMAN"), STEWARD("STEWARD"), ADMINISTRATOR("ADMINISTRATOR"), DISPATCHER("DISPATCHER");

    private final String type;

    PersonType(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
