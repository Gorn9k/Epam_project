package utils.json.write;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import entity.Brigade;
import entity.Flight;
import entity.Person;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BrigadeWriterSerializer implements JsonSerializer<List<Person>> {
    @Override
    public JsonElement serialize(List<Person> persons, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonArray jsonArray = new JsonArray();
        persons.stream().forEach(person -> jsonArray.add(person.getPersonName()));
        return jsonArray;
    }
}
