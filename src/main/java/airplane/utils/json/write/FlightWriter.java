package airplane.utils.json.write;

import airplane.utils.db.ConnectorException;
import com.google.gson.GsonBuilder;
import airplane.dao.postgresql.FlightDaoImpl;
import airplane.entity.Flight;
import airplane.utils.db.Connector;

public class FlightWriter extends JsonWriter<Flight> {
    public FlightWriter() throws ConnectorException {
        GsonBuilder gsonBuilder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting();
        gson = gsonBuilder.create();
        dao = new FlightDaoImpl(Connector.getConnecting());
        propName = "flights";
    }
}
