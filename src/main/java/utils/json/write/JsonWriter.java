package utils.json.write;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import entity.Entity;
import service.Service;
import service.ServiceException;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class JsonWriter<T extends Entity> {
    protected Gson gson;
    protected String propName;
    protected Service<T> service;

    private void writeJson(List<T> data, String filePathName) {
        try (Writer writer = new FileWriter(filePathName)) {
            JsonElement je = gson.toJsonTree(data);
            JsonObject jo = new JsonObject();
            jo.add(propName, je);
            gson.toJson(jo, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exportToJsonFromDB(String filePathName) {
        try {
            writeJson(service.readAll(), filePathName);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }
}
