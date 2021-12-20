package utils.json.write;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import entity.Brigade;
import entity.Person;
import service.logic.BrigadeServiceImpl;

import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.List;

public class BrigadeWriter extends JsonWriter<Brigade>{
    public BrigadeWriter() throws SQLException {
        Type personsListType = new TypeToken<List<Person>>() {}.getType();
        GsonBuilder gsonBuilder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().
                registerTypeAdapter(personsListType, new BrigadeWriterSerializer()).setPrettyPrinting();
        gson = gsonBuilder.create();
        service = new BrigadeServiceImpl();
        propName = "brigades";
    }
}
