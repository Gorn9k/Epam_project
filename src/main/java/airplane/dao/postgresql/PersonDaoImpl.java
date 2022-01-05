package airplane.dao.postgresql;

import airplane.dao.DaoException;
import airplane.dao.PersonDao;
import airplane.entity.Person;
import airplane.entity.PersonType;
import airplane.utils.db.EntityCreator;
import airplane.utils.db.StatementSetter;
import java.sql.*;
import java.util.*;

public class PersonDaoImpl extends BaseDaoImpl implements PersonDao {
    private StatementSetter<Person> statementSetter;
    private EntityCreator<Person> entityCreator;

    public PersonDaoImpl(Connection connection) {
        super(connection);
        statementSetter = (statement, person) -> {
            statement.setString(1, person.getPersonName());
            statement.setString(2, person.getPersonType().getType());
            statement.setBoolean(3, person.isFree());
        };
        entityCreator = resultSet -> {
            Person person = new Person();
            person.setPersonName(resultSet.getString("personName"));
            person.setPersonType(PersonType.valueOf(resultSet.getString("personType")));
            person.setFree(resultSet.getBoolean("isFree"));
            return person;
        };
    }

    @Override
    public Person read(Long id) throws DaoException {
        String sql = "select personName, personType, isFree from persons where id = " + id;
        try (Statement statement = getConnection().createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {
            Person person = null;
            if (resultSet.next()) {
                person = entityCreator.createEntity(resultSet);
                person.setId(id);
            }
            return person;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }



    @Override
    public void save(List<Person> persons) throws DaoException {
        String sql = "insert into persons (personName, personType, isFree) values(?, ?, ?)";
        create(sql, persons, getConnection(), statementSetter);
    }

    @Override
    public void update(Person entity) throws DaoException {
        String sql = "update persons set personName = ?, personType = ?, isFree = ? where id = " + entity.getId();
        update(sql, entity, getConnection(), statementSetter);
    }

    @Override
    public void delete(Long id) throws DaoException {
        String sql = "delete from persons where id = " + id;
        delete(sql, getConnection());
    }

    @Override
    public List<Person> readAll() throws DaoException {
        String sql = "select * from persons";
        try (Statement statement = getConnection().createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {
            List<Person> persons = new ArrayList<>();
            while (resultSet.next()) {
                Person person = entityCreator.createEntity(resultSet);
                person.setId(resultSet.getLong("id"));
                persons.add(person);
            }
            return persons;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Long getMaxId() throws DaoException {
        String sql = "select max(id) from persons";
        return getMaxId(sql, getConnection());
    }

    @Override
    public boolean isPersonExist(Person person) throws DaoException {
        String sql = "select personName, personType, isFree from persons where personName = \'" + person.getPersonName() + "\' and " +
                "personType = \'" + person.getPersonType() + "\' and isFree = " + person.isFree();
        try (Statement statement = getConnection().createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {
            if (resultSet.next()) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
