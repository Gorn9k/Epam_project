package airplane.service.logic;

import airplane.dao.DaoException;
import airplane.dao.PersonDao;
import airplane.entity.Person;
import airplane.service.Service;
import airplane.service.ServiceException;

import java.util.List;

public class PersonServiceImpl implements Service<Person> {
    private PersonDao personDao;

    public PersonServiceImpl(PersonDao personDao) {
        this.personDao = personDao;
    }

    @Override
    public Person findById(Long id) throws ServiceException {
        try {
            if (id != null) {
                return personDao.read(id);
            } else {
                throw new ServiceException("No entity's id was found");
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
                } else {
                    throw new ServiceException("No entity with this identifier was found");
                }
            }
            else {
                throw new ServiceException("Try to update a not existed customer");
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
                throw new ServiceException("No entity's id was found");
            }
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public Long findMaxId() throws ServiceException {
        try {
            return personDao.getMaxId();
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }
}
