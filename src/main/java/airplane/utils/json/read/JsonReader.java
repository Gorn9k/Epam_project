package airplane.utils.json.read;

import airplane.utils.db.ConnectorException;
import com.google.gson.*;
import airplane.dao.DaoException;
import airplane.dao.postgresql.BrigadeDaoImpl;
import airplane.dao.postgresql.FlightDaoImpl;
import airplane.dao.postgresql.PersonDaoImpl;
import airplane.entity.*;
import airplane.utils.db.Connector;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class JsonReader {
    private Gson gson;

    public JsonReader() {
        gson = new Gson();
    }

    private List<Person> readJson(String inputFile, String mainTag) throws IOException {
        List<Person> result = new ArrayList<>();
        try (Reader reader = new FileReader(inputFile)) {
            JsonElement je = JsonParser.parseReader(reader);
            JsonObject jo = je.getAsJsonObject();
            JsonArray arr = jo.getAsJsonArray(mainTag);
            for (JsonElement jel : arr) {
                result.add(gson.fromJson(jel, Person.class));
            }
        } catch (IOException e) {
            throw new IOException(e);
        }
        return result;
    }

    public void importToDBFromJson(String inputFile, String mainTag) throws DaoException, IOException, ConnectorException {
        PersonDaoImpl personDao = new PersonDaoImpl(Connector.getConnecting());
        FlightDaoImpl flightDao = new FlightDaoImpl(Connector.getConnecting());
        BrigadeDaoImpl brigadeDao = new BrigadeDaoImpl(Connector.getConnecting());
        AtomicLong maxIdOfPersons = new AtomicLong(personDao.getMaxId());
        AtomicLong maxIdOfBrigades = new AtomicLong(brigadeDao.getMaxId());
        List<Person> persons = readJson(inputFile, mainTag).stream().peek(person ->
                person.setId(maxIdOfPersons.getAndIncrement())).collect(Collectors.toList());;
        List<Brigade> brigades = new ArrayList<>();
        List<Flight> flightList = new ArrayList<>();
        persons.stream().map(Person::getFlights).forEach(flights -> {
            if (flights != null) {
                Brigade brigade = new Brigade();
                persons.stream().filter(person -> person.getFlights() != null && person.getFlights().equals(flights) &&
                        person.isFree()).forEach(person -> {
                    person.setFree(false);
                    brigade.getPersons().add(person);
                });
                if (brigade.getPersons().stream().anyMatch(person -> person.getPersonType() == PersonType.PILOT) &&
                        brigade.getPersons().stream().anyMatch(person -> person.getPersonType() == PersonType.NAVIGATOR) &&
                        brigade.getPersons().stream().anyMatch(person -> person.getPersonType() == PersonType.RADIOMAN)) {
                    brigade.setId(maxIdOfBrigades.getAndIncrement());
                    Collections.sort(brigade.getPersons());
                    brigades.add(brigade);
                    flights.forEach(flight -> flight.setBrigade(brigade));
                    flightList.addAll(flights);
                }
            }
        });
        personDao.save(persons);
        brigadeDao.save(brigades);
        flightDao.save(flightList);
    }
}
