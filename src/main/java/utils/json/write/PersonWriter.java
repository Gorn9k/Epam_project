package utils.json.write;

import com.google.gson.GsonBuilder;
import dao.postgresql.PersonDaoImpl;
import entity.Person;
import utils.db.Connector;
import java.sql.SQLException;

public class PersonWriter extends JsonWriter<Person> {
    public PersonWriter() throws SQLException {
        GsonBuilder gsonBuilder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting();
        gson = gsonBuilder.create();
        dao = new PersonDaoImpl(Connector.getConnection());
        propName = "persons";
    }
}
