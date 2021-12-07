package utils.json.read;

import com.google.gson.Gson;
import entity.Flight;
import service.logic.FlightServiceImpl;

public class FlightJsonReader extends JsonReader<Flight> {

    public FlightJsonReader() {
        gson = new Gson();
        service = new FlightServiceImpl();
        aClass = Flight.class;
    }
}
