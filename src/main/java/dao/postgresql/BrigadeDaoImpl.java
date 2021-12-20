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

public class BrigadeDaoImpl extends BaseDaoImpl implements Dao<Brigade> {
    private StatementSetter<Brigade> statementSetter;
    private EntityCreator<Brigade> entityCreator;

    public BrigadeDaoImpl(Connection connection) {
        super(connection);
        statementSetter = (statement, brigade) -> {
            for (int i = 0; i < brigade.getPersons().size(); i++) {
                statement.setLong(i + 1, brigade.getPersons().get(i).getId());
            }
        };
        entityCreator = resultSet -> {
            Brigade brigade = new Brigade();
            while (true) {
                brigade.getPersons().add(new Person(resultSet.getString("personName"),
                        PersonType.valueOf(resultSet.getString("personType")),
                        resultSet.getBoolean("isFree")));
                if (!resultSet.next()) {
                    break;
                }
            }
            return brigade;
        };
    }

    @Override
    public Brigade read(Long id) throws DaoException {
        String sqlForPersons = "select persons.id, persons.personType, persons.personName, persons.isFree " +
                "from persons inner join brigades on persons.id = brigades.idPilot or persons.id = brigades.idNavigator or " +
                "persons.id = brigades.idRadioman or persons.id = brigades.idFirstSteward or persons.id = brigades.idSecondSteward " +
                "where brigades.id = " + id;
        String sqlForFlights = "select id, flightName, idBrigade from flights where idBrigade = " + id;
        Brigade brigade;
        try (PreparedStatement preparedStatementForPersons = getConnection().prepareStatement(sqlForPersons);
             ResultSet resultSetForPersons = preparedStatementForPersons.executeQuery();
             PreparedStatement preparedStatementForFlights = getConnection().prepareStatement(sqlForFlights);
             ResultSet resultSetForFlights = preparedStatementForFlights.executeQuery()) {
            if (resultSetForPersons.next()) {
                brigade = entityCreator.createEntity(resultSetForPersons);
                brigade.setId(id);
            } else {
                return null;
            }
            while (resultSetForFlights.next()) {
                brigade.getFlights().add(
                        new Flight(brigade, resultSetForFlights.getString("flightName")));
            }
            return brigade;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void save(List<Brigade> brigades) throws DaoException {
        boolean isDifferent = brigades.stream().anyMatch(brigade -> brigade.getPersons().size() != Brigade.DEFAULT_SIZE_OF_BRIGADE);
        if (isDifferent) {
            for (Brigade brigade : brigades) {
                List<Brigade> brigadeList = new ArrayList<Brigade>() {{
                    add(brigade);
                }};
                String sql;
                switch (brigade.getPersons().size()) {
                    case 3:
                        sql = "insert into brigades (idPilot, idNavigator, idRadioman) values(?, ?, ?)";
                        create(sql, brigadeList, getConnection(), statementSetter);
                        break;
                    case 4:
                        sql = "insert into brigades (idPilot, idNavigator, idRadioman, idFirstSteward) values(?, ?, ?, ?)";
                        create(sql, brigadeList, getConnection(), statementSetter);
                        break;
                    case 5:
                        sql = "insert into brigades (idPilot, idNavigator, idRadioman, idFirstSteward, idSecondSteward) values(?, ?, ?, ?, ?)";
                        create(sql, brigadeList, getConnection(), statementSetter);
                        break;
                }
            }
        } else {
            String sql = "insert into brigades (idPilot, idNavigator, idRadioman, idFirstSteward, idSecondSteward) values(?, ?, ?, ?, ?)";
            create(sql, brigades, getConnection(), statementSetter);
        }
    }

    @Override
    public void update(Brigade entity) throws DaoException {
        String sql = "update brigades set idPilot = ?, idNavigator = ?, idRadioman = ?, idFirstSteward = ?, " +
                "idSecondSteward = ? where id = " + entity.getId();
        update(sql, entity, getConnection(), statementSetter);
    }

    @Override
    public void delete(Long id) throws DaoException {
        String sql = "delete from brigades where id = " + id;
        delete(sql, getConnection());
    }

    @Override
    public List<Brigade> readAll() throws DaoException {
        String sqlForBrigades = "select id from brigades";
        try (Statement statement = getConnection().createStatement(); ResultSet resultSet = statement.executeQuery(sqlForBrigades)) {
            List<Brigade> brigades = new ArrayList<>();
            while (resultSet.next()) {
                brigades.add(read(resultSet.getLong("id")));
            }
            return brigades;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Long getMaxId() throws DaoException {
        String sql = "select max(id) from brigades";
        return getMaxId(sql, getConnection());
    }
}
