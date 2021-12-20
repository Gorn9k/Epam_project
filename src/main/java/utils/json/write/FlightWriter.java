package utils.json.write;

import com.google.gson.GsonBuilder;
import entity.Flight;
import service.logic.FlightServiceImpl;

import java.sql.SQLException;

public class FlightWriter extends JsonWriter<Flight> {
    public FlightWriter() throws SQLException {
        GsonBuilder gsonBuilder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting();
        gson = gsonBuilder.create();
        service = new FlightServiceImpl();
        propName = "flights";
    }
}
