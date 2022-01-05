package airplane.utils.json.write;

import airplane.utils.db.ConnectorException;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import airplane.dao.postgresql.BrigadeDaoImpl;
import airplane.entity.Brigade;
import airplane.entity.Person;
import airplane.utils.db.Connector;
import java.lang.reflect.Type;
import java.util.List;

public class BrigadeWriter extends JsonWriter<Brigade>{
    public BrigadeWriter() throws ConnectorException {
        Type personsListType = new TypeToken<List<Person>>() {}.getType();
        GsonBuilder gsonBuilder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().
                registerTypeAdapter(personsListType, new BrigadeWriterSerializer()).setPrettyPrinting();
        gson = gsonBuilder.create();
        dao = new BrigadeDaoImpl(Connector.getConnecting());
        propName = "brigades";
    }
}
