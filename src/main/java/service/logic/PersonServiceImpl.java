package service.logic;

import dao.DaoException;
import dao.postgresql.PersonDaoImpl;
import entity.Person;
import service.Service;
import service.ServiceException;
import java.util.List;

public class PersonServiceImpl implements Service<Person> {
    private PersonDaoImpl personDao;

    @Override
    public Person read(Integer id) throws ServiceException {
        personDao = new PersonDaoImpl();
        try {
            if (id != null) {
                return personDao.read(id);
            } else {
                throw new ServiceException();
            }
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public List<Person> readAll() throws ServiceException {
        personDao = new PersonDaoImpl();
        try {
            return personDao.readAll();
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public void save(Person entity) throws ServiceException {
        personDao = new PersonDaoImpl();
        try {
            if (entity != null && entity.getPersonName() != null && entity.getPersonType() != null && entity.isFree()) {
                personDao.create(entity);
            } else {
                throw new ServiceException();
            }
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public void edit(Person entity) throws ServiceException {
        personDao = new PersonDaoImpl();
        try {
            if (entity != null && entity.getId() != null) {
                Person person = personDao.read(entity.getId());
                if (person != null) {
                    person.setPersonName(entity.getPersonName() == null ? person.getPersonName() : entity.getPersonName());
                    person.setPersonType(entity.getPersonType() == null ? person.getPersonType() : entity.getPersonType());
                    person.setFree(entity.isFree());
                    personDao.update(person);
                }
            } else {
                throw new ServiceException();
            }
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public void delete(Integer id) throws ServiceException {
        personDao = new PersonDaoImpl();
        try {
            personDao.delete(id);
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }
}
