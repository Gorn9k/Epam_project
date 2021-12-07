package utils.json.write;

import com.google.gson.GsonBuilder;
import entity.Flight;
import service.logic.FlightServiceImpl;

public class FlightWriter extends JsonWriter<Flight> {
    public FlightWriter() {
        GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();
        gson = gsonBuilder.create();
        service = new FlightServiceImpl();
        propName = "flights";
    }
}
