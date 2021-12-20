package utils.json.write;

import com.google.gson.GsonBuilder;
import entity.Person;
import service.logic.PersonServiceImpl;

import java.sql.SQLException;

public class PersonWriter extends JsonWriter<Person> {
    public PersonWriter() throws SQLException {
        GsonBuilder gsonBuilder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting();
        gson = gsonBuilder.create();
        service = new PersonServiceImpl();
        propName = "persons";
    }
}
