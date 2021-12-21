package utils.json.write;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import dao.postgresql.BrigadeDaoImpl;
import entity.Brigade;
import entity.Person;
import utils.db.Connector;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.List;

public class BrigadeWriter extends JsonWriter<Brigade>{
    public BrigadeWriter() throws SQLException {
        Type personsListType = new TypeToken<List<Person>>() {}.getType();
        GsonBuilder gsonBuilder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().
                registerTypeAdapter(personsListType, new BrigadeWriterSerializer()).setPrettyPrinting();
        gson = gsonBuilder.create();
        dao = new BrigadeDaoImpl(Connector.getConnection());
        propName = "brigades";
    }
}
