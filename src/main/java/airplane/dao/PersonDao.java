package airplane.dao;

import airplane.entity.Person;

public interface PersonDao extends Dao<Person> {
    boolean isPersonExist(Person person) throws DaoException;
}
