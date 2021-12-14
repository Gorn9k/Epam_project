package dao.postgresql;

import dao.Dao;
import dao.DaoException;
import entity.Brigade;
import entity.Flight;
import entity.Person;
import entity.PersonType;
import utils.db.EntityCreator;
import utils.db.StatementSetter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FlightDaoImpl extends BaseDaoImpl implements Dao<Flight> {
    private StatementSetter<Flight> statementSetter;
    private EntityCreator<Flight> entityCreator;

    public FlightDaoImpl(Connection connection) {
        super(connection);
        statementSetter = (statement, flight) -> {
            statement.setString(1, flight.getFlightName());
            statement.setLong(2, flight.getBrigade().getId());
        };
        entityCreator = resultSet -> {
            Flight flight = new Flight();
            flight.setFlightName(resultSet.getString("flightName"));
            return flight;
        };
    }

    @Override
    public Flight read(Long id) throws DaoException {
        String sqlForFlights = "select flightName, idBrigade from flights where id = " + id;
        StringBuilder sqlForPersons = new StringBuilder("select persons.id, persons.personType, persons.personName, persons.isFree " +
                "from persons inner join brigades on persons.id = brigades.idPilot or persons.id = brigades.idNavigator or " +
                "persons.id = brigades.idRadioman or persons.id = brigades.idFirstSteward or persons.id = brigades.idSecondSteward " +
                "where brigades.id = ");
        try (PreparedStatement preparedStatementForFlights = getConnection().prepareStatement(sqlForFlights + id);
             ResultSet resultSetForFlights = preparedStatementForFlights.executeQuery()) {
            Flight flight = null;
            Brigade brigade;
            if (resultSetForFlights.next()) {
                flight = entityCreator.createEntity(resultSetForFlights);
                flight.setId(id);
                if (resultSetForFlights.getString("idBrigade") != null) {
                    try (PreparedStatement preparedStatementForPersons = getConnection().
                            prepareStatement(String.valueOf(sqlForPersons.append(resultSetForFlights.getString("idBrigade"))));
                         ResultSet resultSetForPersons = preparedStatementForPersons.executeQuery()) {
                        brigade = new Brigade();
                        while (resultSetForPersons.next()) {
                            brigade.addPerson(new Person(resultSetForPersons.getLong("id"),
                                    resultSetForPersons.getString("personName"),
                                    PersonType.valueOf(resultSetForPersons.getString("personType")),
                                    resultSetForPersons.getBoolean("isFree")));
                        }
                        flight.setBrigade(brigade);
                    }
                }
            }
            return flight;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void save(List<Flight> entities) throws DaoException {
        String sql = "insert into flights (flightName, idBrigade) values(?, ?)";
        create(sql, entities, getConnection(), statementSetter);
    }

    @Override
    public void update(Flight entity) throws DaoException {
        String sql = "update flights set flightName = ?, idBrigade = ? where id = " + entity.getId();
        update(sql, entity, getConnection(), statementSetter);
    }

    @Override
    public void delete(Long id) throws DaoException {
        String sql = "delete from flights where id = " + id;
        delete(sql, getConnection());
    }

    @Override
    public List<Flight> readAll() throws DaoException {
        String sql = "select id from flights";
        try (Statement statement = getConnection().createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {
            List<Flight> flights = new ArrayList<>();
            while (resultSet.next()) {
                flights.add(read(resultSet.getLong("id")));
            }
            return flights;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
