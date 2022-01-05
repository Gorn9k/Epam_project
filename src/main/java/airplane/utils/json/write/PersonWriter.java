package airplane.utils.json.write;

import airplane.utils.db.ConnectorException;
import com.google.gson.GsonBuilder;
import airplane.dao.postgresql.PersonDaoImpl;
import airplane.entity.Person;
import airplane.utils.db.Connector;

public class PersonWriter extends JsonWriter<Person> {
    public PersonWriter() throws ConnectorException {
        GsonBuilder gsonBuilder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting();
        gson = gsonBuilder.create();
        dao = new PersonDaoImpl(Connector.getConnecting());
        propName = "persons";
    }
}
