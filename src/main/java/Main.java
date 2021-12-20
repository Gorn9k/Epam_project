import dao.DaoException;
import service.ServiceException;
import utils.db.Connector;
import utils.json.write.BrigadeWriter;
import utils.json.write.FlightWriter;
import utils.json.write.JsonWriter;
import utils.json.write.PersonWriter;

import java.sql.SQLException;
import java.text.ParseException;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, SQLException, DaoException, ParseException, ServiceException {
        Connector.init("org.postgresql.Driver", "jdbc:postgresql://localhost/airplane",
                "postgres", "635756");
        //JsonReader jsonReader = new JsonReader();
        //jsonReader.importToDBFromJson("7.airline.json", "persons");
        JsonWriter jsonWriter = new PersonWriter();
        jsonWriter.exportToJsonFromDB("persons.json");
        jsonWriter = new FlightWriter();
        jsonWriter.exportToJsonFromDB("flights.json");
        jsonWriter = new BrigadeWriter();
        jsonWriter.exportToJsonFromDB("brigades.json");
        //new MainView().runMainView();
//        System.out.println(Arrays.stream(new Person[]{new Person(1 ,"alexei", PersonType.PILOT, true)}).
//                allMatch(person -> {
//                    try {
//                        Person person1 = new PersonServiceImpl().read(person.getId());
//                        System.out.println(person1);
//                        System.out.println(person);
//                        return person.equals(person1);
//                    } catch (ServiceException e) {
//                        e.printStackTrace();
//                        return false;
//                    }
//                }));
//        PersonDaoImpl personDao = new PersonDaoImpl();
//        BrigadeDaoImpl brigadeDao = new BrigadeDaoImpl();
//        FlightDaoImpl flightDao = new FlightDaoImpl();
//        Brigade brigade1 = new Brigade();
//        brigade1.addPerson(personDao.read(1));
//        brigade1.addPerson(personDao.read(3));
//        brigade1.addPerson(personDao.read(5));
//        brigade1.addPerson(personDao.read(7));
//        brigade1.addPerson(personDao.read(9));
//        brigadeDao.create(brigade1);
//        brigade1 = brigadeDao.read(1);
//        Brigade brigade2 = new Brigade();
//        brigade2.addPerson(personDao.read(2));
//        brigade2.addPerson(personDao.read(4));
//        brigade2.addPerson(personDao.read(6));
//        brigade2.addPerson(personDao.read(8));
//        brigade2.addPerson(personDao.read(10));
//        brigadeDao.create(brigade2);
//        brigade2 = brigadeDao.read(2);
//        flightDao.create(new Flight(brigade1,"123"));
//        flightDao.create(new Flight(brigade2,"123312321312"));
//        List<? extends BaseEntity> baseEntities;
//        baseEntities = personDao.readAll();
//        baseEntities.forEach(System.out::println);
//        System.out.println();
//        baseEntities = brigadeDao.readAll();
//        baseEntities.forEach(System.out::println);
//        System.out.println();
//        baseEntities = flightDao.readAll();
//        baseEntities.forEach(System.out::println);
        //List<Brigade> brigades = brigadeDao.readAll();
        //brigades.forEach(System.out::println);
        //System.out.println(brigade);
        //brigade.addPerson(personDao.read(2));
        //brigade.addPerson(personDao.read(4));
        //brigade.addPerson(personDao.read(5));
        //brigade.addPerson(personDao.read(1));
        //brigade.addPerson(personDao.read(6));
        //new BrigadeDaoImpl().create(brigade);
//        JsonReader jr = new PersonJsonReader();
//        List<Person> personList = jr.readJson("7.airline.json", "persons");
//        personList.forEach(System.out::println);
//        jr = new FlightJsonReader();
//        System.out.println();
//        List<Flight> flightList = jr.readJson("7.airline.json", "persons", "flights");
//        flightList.forEach(System.out::println);
//        JsonWriter jw = new PersonWriter();
//        jw.writeJson(personList, "persons.json");
//        jw = new FlightWriter();
//        jw.writeJson(flightList, "flights.json");
    }
}
