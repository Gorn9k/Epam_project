package dao.postgresql;

import dao.Dao;
import dao.DaoException;
import entity.Brigade;
import entity.Flight;
import entity.Person;
import entity.PersonType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import static utils.db.Connector.getConnection;

public class FlightDaoImpl implements Dao<Flight> {

    @Override
    public Flight read(Integer id) throws DaoException {
        StringBuilder sqlForFlights = new StringBuilder("select flightName, idBrigade from flights where id = ");
        StringBuilder sqlForPersons = new StringBuilder("select persons.id, persons.personType, persons.personName, persons.isFree " +
                "from (persons inner join brigades on persons.id = brigades.idPilot or persons.id = brigades.idNavigator or " +
                "persons.id = brigades.idRadioman or persons.id = brigades.idFirstSteward or persons.id = brigades.idSecondSteward) " +
                "where brigades.id = ");
        try (PreparedStatement preparedStatementForFlights = getConnection().prepareStatement(String.valueOf(sqlForFlights.append(id)));
             ResultSet resultSetForFlights = preparedStatementForFlights.executeQuery()) {
            Flight flight = null;
            Brigade brigade = null;
            if (resultSetForFlights.next()) {
                flight = new Flight();
                flight.setId(id);
                flight.setFlightName(resultSetForFlights.getString("flightName"));
                try (PreparedStatement preparedStatementForPersons = getConnection().
                        prepareStatement(String.valueOf(sqlForPersons.append(resultSetForFlights.getString("idBrigade"))));
                ResultSet resultSetForPersons = preparedStatementForPersons.executeQuery()) {
                    if (resultSetForPersons.next()) {
                        brigade = new Brigade();
                        while (true) {
                            brigade.addPerson(new Person(resultSetForPersons.getInt("id"),
                                    resultSetForPersons.getString("personName"),
                                    PersonType.valueOf(resultSetForPersons.getString("personType")),
                                    resultSetForPersons.getBoolean("isFree")));
                            if (!resultSetForPersons.next()) {
                                break;
                            }
                        }
                    }
                }
                flight.setBrigade(brigade);
            }
            return flight;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void create(Flight entity) throws DaoException {
        String sql = "insert into flights (id, flightName, idBrigade) values(?, ?, ?)";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            Integer id = getMaxIdFromTable("flights");
            statement.setInt(1, id);
            statement.setString(2, entity.getFlightName());
            statement.setInt(3, entity.getBrigade().getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(Flight entity) throws DaoException {
        String sql = "update flights set flightName = ?, idBrigade = ? where id = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setString(1, entity.getFlightName());
            statement.setInt(2, entity.getBrigade().getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void delete(Integer id) throws DaoException {
        String sql = "delete from flights where id = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Flight> readAll() throws DaoException {
        String sql = "select * from flights";
        try (Statement statement = getConnection().createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {
            List<Flight> flights = new ArrayList<>();
            while (resultSet.next()) {
                flights.add(read(resultSet.getInt("id")));
            }
            return flights;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
