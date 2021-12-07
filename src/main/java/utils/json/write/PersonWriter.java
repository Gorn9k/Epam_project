package utils.json.write;

import com.google.gson.GsonBuilder;
import entity.Person;
import service.logic.PersonServiceImpl;

public class PersonWriter extends JsonWriter<Person> {
    public PersonWriter() {
        GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();
        gson = gsonBuilder.create();
        service = new PersonServiceImpl();
        propName = "persons";
    }
}
