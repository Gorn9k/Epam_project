package utils.json.read;

import com.google.gson.Gson;
import entity.Person;
import service.logic.PersonServiceImpl;

public class PersonJsonReader extends JsonReader<Person> {

    public PersonJsonReader() {
        gson = new Gson();
        service = new PersonServiceImpl();
        aClass = Person.class;
    }
}
