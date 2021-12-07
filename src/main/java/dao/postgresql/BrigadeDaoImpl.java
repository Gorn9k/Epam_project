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

public class BrigadeDaoImpl implements Dao<Brigade> {

    @Override
    public Brigade read(Integer id) throws DaoException {
        StringBuilder sqlForPersons = new StringBuilder("select persons.id, persons.personType, persons.personName, persons.isFree " +
                "from (persons inner join brigades on persons.id = brigades.idPilot or persons.id = brigades.idNavigator or " +
                "persons.id = brigades.idRadioman or persons.id = brigades.idFirstSteward or persons.id = brigades.idSecondSteward) " +
                "where brigades.id = ");
        StringBuilder sqlForFlights = new StringBuilder("select id, flightName, idBrigade from flights where idBrigade = ");
        Brigade brigade = null;
        try (PreparedStatement preparedStatementForPersons = getConnection().prepareStatement(String.valueOf(sqlForPersons.append(id)));
             ResultSet resultSetForPersons = preparedStatementForPersons.executeQuery();
             PreparedStatement preparedStatementForFlights = getConnection().prepareStatement(String.valueOf(sqlForFlights.append(id)));
             ResultSet resultSetForFlights = preparedStatementForFlights.executeQuery()) {
            if (resultSetForPersons.next()) {
                brigade = new Brigade();
                brigade.setId(id);
                while (true) {
                    brigade.addPerson(
                            new Person(
                                    resultSetForPersons.getString("personName"),
                                    PersonType.valueOf(resultSetForPersons.getString("personType")),
                                    resultSetForPersons.getBoolean("isFree")));
                    if (!resultSetForPersons.next()) {
                        break;
                    }
                }
            }
            while (resultSetForFlights.next()) {
                brigade.addFlight(
                        new Flight(brigade, resultSetForFlights.getString("flightName")));
            }
            return brigade;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void create(Brigade entity) throws DaoException {
        String sql = "insert into brigades (id, idPilot, idNavigator, idRadioman, idFirstSteward, idSecondSteward) values(?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            Integer id = getMaxIdFromTable("brigades");
            statement.setInt(1, id);
            statement.setInt(2, entity.getPersons()[0].getId());
            statement.setInt(3, entity.getPersons()[1].getId());
            statement.setInt(4, entity.getPersons()[2].getId());
            statement.setInt(5, entity.getPersons()[3].getId());
            statement.setInt(6, entity.getPersons()[4].getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(Brigade entity) throws DaoException {
        String sql = "update brigades set idPilot = ?, idNavigator = ?, idRadioman = ?, idFirstSteward = ?, idSecondSteward = ? where id = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setString(1, entity.getPersons()[0].getPersonType().getType());
            statement.setString(2, entity.getPersons()[1].getPersonType().getType());
            statement.setString(3, entity.getPersons()[2].getPersonType().getType());
            statement.setString(4, entity.getPersons()[3].getPersonType().getType());
            statement.setString(5, entity.getPersons()[4].getPersonType().getType());
            statement.setInt(5, entity.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void delete(Integer id) throws DaoException {
        String sql = "delete from brigades where id = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Brigade> readAll() throws DaoException {
        String sqlForBrigades = "select * from brigades";
        try (Statement statement = getConnection().createStatement(); ResultSet resultSet = statement.executeQuery(sqlForBrigades)) {
            List<Brigade> brigades = new ArrayList<>();
            while (resultSet.next()) {
                brigades.add(read(resultSet.getInt("id")));
            }
            return brigades;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
