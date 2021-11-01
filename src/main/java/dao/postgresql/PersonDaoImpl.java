package dao.postgresql;

import dao.DaoException;
import dao.PersonDao;
import entity.Person;

import java.util.List;
import java.util.UUID;

public class PersonDaoImpl implements PersonDao {
    @Override
    public Person read(UUID id) throws DaoException {
        return null;
    }

    @Override
    public Long create(Person entity) throws DaoException {
        return null;
    }

    @Override
    public void update(Person entity) throws DaoException {

    }

    @Override
    public void delete(UUID id) throws DaoException {

    }

    @Override
    public List<Person> readAll() {
        return null;
    }
}
