package utils.json.read;

import com.google.gson.*;
import entity.*;
import service.ServiceException;
import service.logic.BrigadeServiceImpl;
import service.logic.FlightServiceImpl;
import service.logic.PersonServiceImpl;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class JsonReader {
    private Gson gson;
    private PersonServiceImpl personService;
    private FlightServiceImpl flightService;
    private BrigadeServiceImpl brigadeService;

    public JsonReader() throws SQLException {
        gson = new Gson();
        personService = new PersonServiceImpl();
        flightService = new FlightServiceImpl();
        brigadeService = new BrigadeServiceImpl();
    }

    public List<Person> readJson(String inputFile, String mainTag) {
        List<Person> result = new ArrayList<>();
        try (Reader reader = new FileReader(inputFile)) {
            JsonElement je = JsonParser.parseReader(reader);
            JsonObject jo = je.getAsJsonObject();
            JsonArray arr = jo.getAsJsonArray(mainTag);
            for (JsonElement jel : arr) {
                result.add(gson.fromJson(jel, Person.class));
            }
        } catch (IOException e) {
            e.printStackTrace();
            result.clear();
        }
        return result;
    }

//    public List<T> readJson(String inputFile, String mainTag, String tagName) {
//        List<T> result = new ArrayList<>();
//        try (Reader reader = new FileReader(inputFile)) {
//            JsonElement je = JsonParser.parseReader(reader);
//            JsonObject jo = je.getAsJsonObject();
//            JsonArray arr = jo.getAsJsonArray(mainTag);
//            JsonArray pArray;
//            for (JsonElement jel : arr) {
//                pArray = jel.getAsJsonObject().getAsJsonArray(tagName);
//                if (pArray != null) {
//                    pArray.forEach(jet -> result.add(gson.fromJson(jet, aClass)));
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            result.clear();
//        }
//        return result;
//    }

    public void importToDBFromJson(String inputFile, String mainTag) throws ServiceException {
        AtomicLong maxIdOfPersons = new AtomicLong(personService.findMaxId());
        AtomicLong maxIdOfBrigades = new AtomicLong(brigadeService.findMaxId());
        List<Person> persons = readJson(inputFile, mainTag).stream().peek(person ->
                person.setId(maxIdOfPersons.getAndIncrement())).collect(Collectors.toList());;
        List<Brigade> brigades = new ArrayList<>();
        List<Flight> flightList = new ArrayList<>();
        persons.stream().map(Person::getFlights).forEach(flights -> {
            if (flights != null) {
                Brigade brigade = new Brigade();
                persons.stream().filter(person -> person.getFlights() != null && person.getFlights().equals(flights) && person.isFree()).forEach(person -> {
                    person.setFree(false);
                    brigade.getPersons().add(person);
                });
                if (brigade.getPersons().stream().anyMatch(person -> person.getPersonType() == PersonType.PILOT) &&
                        brigade.getPersons().stream().anyMatch(person -> person.getPersonType() == PersonType.NAVIGATOR) &&
                        brigade.getPersons().stream().anyMatch(person -> person.getPersonType() == PersonType.RADIOMAN)) {
                    brigade.setId(maxIdOfBrigades.getAndIncrement());
                    brigades.add(brigade);
                    flights.forEach(flight -> flight.setBrigade(brigade));
                    flightList.addAll(flights);
                }
            }
        });
        personService.create(persons);
        brigadeService.create(brigades);
        flightService.create(flightList);
    }
}
