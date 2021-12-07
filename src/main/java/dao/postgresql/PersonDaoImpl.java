package dao.postgresql;

import dao.Dao;
import dao.DaoException;
import entity.Person;
import entity.PersonType;
import java.sql.*;
import java.util.*;
import static utils.db.Connector.getConnection;

public class PersonDaoImpl implements Dao<Person> {

    @Override
    public Person read(Integer id) throws DaoException {
        StringBuilder sql = new StringBuilder("select personType, personName, isFree from persons where id = ");
        try(PreparedStatement statement = getConnection().prepareStatement(String.valueOf(sql.append(id)));
            ResultSet resultSet = statement.executeQuery()) {
            Person person = null;
            if(resultSet.next()) {
                person = new Person();
                person.setId(id);
                person.setPersonType(PersonType.valueOf(resultSet.getString("personType")));
                person.setPersonName(resultSet.getString("personName"));
                person.setFree(resultSet.getBoolean("isFree"));
            }
            return person;
        } catch(SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void create(Person entity) throws DaoException {
        String sql = "insert into persons (id, personType, personName, isFree) values(?, ?, ?, ?)";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            Integer id = getMaxIdFromTable("persons");
            statement.setInt(1, id);
            statement.setString(2, entity.getPersonType().getType());
            statement.setString(3, entity.getPersonName());
            statement.setBoolean(4, entity.isFree());
            statement.executeUpdate();
        } catch(SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(Person entity) throws DaoException {
        String sql = "update persons set personType = ?, personName = ?, isFree = ? where id = ?";
        try(PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setString(1, entity.getPersonType().getType());
            statement.setString(2, entity.getPersonName());
            statement.setBoolean(3, entity.isFree());
            statement.setInt(4, entity.getId());
            statement.executeUpdate();
        } catch(SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void delete(Integer id) throws DaoException {
        String sql = "delete from persons where id = ?";
        try(PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch(SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Person> readAll() throws DaoException {
        String sql = "select * from persons";
        try(Statement statement = getConnection().createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {
            List<Person> persons = new ArrayList<>();
            while(resultSet.next()) {
                Person person = new Person();
                person.setId(resultSet.getInt("id"));
                person.setPersonType(PersonType.valueOf(resultSet.getString("personType")));
                person.setPersonName(resultSet.getString("personName"));
                person.setFree(resultSet.getBoolean("isFree"));
                persons.add(person);
            }
            return persons;
        } catch(SQLException e) {
            throw new DaoException(e);
        }
    }
}
