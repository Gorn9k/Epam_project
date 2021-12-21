package utils.json.write;

import com.google.gson.GsonBuilder;
import dao.postgresql.FlightDaoImpl;
import entity.Flight;
import utils.db.Connector;
import java.sql.SQLException;

public class FlightWriter extends JsonWriter<Flight> {
    public FlightWriter() throws SQLException {
        GsonBuilder gsonBuilder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting();
        gson = gsonBuilder.create();
        dao = new FlightDaoImpl(Connector.getConnection());
        propName = "flights";
    }
}
