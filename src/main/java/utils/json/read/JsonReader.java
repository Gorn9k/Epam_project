package utils.json.read;

import com.google.gson.*;
import entity.Entity;
import service.Service;
import service.ServiceException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public abstract class JsonReader<T extends Entity> {
    protected Gson gson;
    protected Class<T> aClass;
    protected Service<T> service;

    private List<T> readJson(String inputFile, String mainTag) {
        List<T> result = new ArrayList<>();
        try (Reader reader = new FileReader(inputFile)) {
            JsonElement je = JsonParser.parseReader(reader);
            JsonObject jo = je.getAsJsonObject();
            JsonArray arr = jo.getAsJsonArray(mainTag);
            for (JsonElement jel : arr) {
                result.add(gson.fromJson(jel, aClass));
            }
        } catch (IOException e) {
            e.printStackTrace();
            result.clear();
        }
        return result;
    }

    private List<T> readJson(String inputFile, String mainTag, String tagName) {
        List<T> result = new ArrayList<>();
        try (Reader reader = new FileReader(inputFile)) {
            JsonElement je = JsonParser.parseReader(reader);
            JsonObject jo = je.getAsJsonObject();
            JsonArray arr = jo.getAsJsonArray(mainTag);
            JsonArray pArray;
            for (JsonElement jel : arr) {
                pArray = jel.getAsJsonObject().getAsJsonArray(tagName);
                if (pArray != null) {
                    pArray.forEach(jet -> result.add(gson.fromJson(jet, aClass)));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            result.clear();
        }
        return result;
    }

    public void importToDBFromJson(String inputFile, String mainTag) {
        List<T> entities = readJson(inputFile, mainTag);
        entities.forEach(entity -> {
            try {
                service.save(entity);
            } catch (ServiceException e) {
                e.printStackTrace();
            }
        });
    }
}
