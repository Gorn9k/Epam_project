package airplane.utils.json.write;

import com.google.gson.*;
import airplane.entity.Person;
import java.lang.reflect.Type;
import java.util.List;

public class BrigadeWriterSerializer implements JsonSerializer<List<Person>> {
    @Override
    public JsonElement serialize(List<Person> persons, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonArray jsonArray = new JsonArray();
        persons.stream().forEach(person -> {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("personName", person.getPersonName());
            jsonArray.add(jsonObject);
        });
        return jsonArray;
    }
}
