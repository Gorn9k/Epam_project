package utils.json.write;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dao.Dao;
import dao.DaoException;
import entity.Entity;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

public abstract class JsonWriter<T extends Entity> {
    protected Gson gson;
    protected String propName;
    protected Dao<T> dao;

    private void writeJson(List<T> data, String filePathName) throws IOException {
        try (Writer writer = new FileWriter(filePathName)) {
            JsonElement je = gson.toJsonTree(data);
            JsonObject jo = new JsonObject();
            jo.add(propName, je);
            gson.toJson(jo, writer);
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    public void exportToJsonFromDB(String filePathName) throws DaoException, IOException {
        try {
            writeJson(dao.readAll(), filePathName);
        } catch (DaoException e) {
            throw new DaoException(e);
        }
    }
}
