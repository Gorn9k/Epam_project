package dao;

import entity.Person;

public interface PersonDao extends Dao<Person> {
    boolean isPersonExist(Person person) throws DaoException;
}
