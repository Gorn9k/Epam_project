package service.logic;

import dao.DaoException;
import dao.postgresql.PersonDaoImpl;
import entity.Entity;
import entity.Person;
import service.Service;
import service.ServiceException;

import java.util.List;
import java.util.Optional;

public class PersonServiceImpl implements Service<Person> {
    private PersonDaoImpl personDao;

    @Override
    public Optional<Person> findById(Long id) throws ServiceException {
        try {
            if (id != null) {
                return Optional.ofNullable(personDao.read(id));
            } else {
                throw new ServiceException();
            }
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public List<Person> findAll() throws ServiceException {
        try {
            return personDao.readAll();
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public void create(List<Person> entities) throws ServiceException {
        try {
            if (entities == null || entities.isEmpty()) {
                throw new ServiceException("There are no users to store");
            }
            personDao.save(entities);
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public void edit(Person entity) throws ServiceException {
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
    public void delete(Long id) throws ServiceException {
        try {
            if (id != null) {
                personDao.delete(id);
            } else {
                throw new ServiceException();
            }
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }
}
