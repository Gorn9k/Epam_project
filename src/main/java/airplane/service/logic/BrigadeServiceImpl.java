package airplane.service.logic;

import airplane.dao.BrigadeDao;
import airplane.dao.DaoException;
import airplane.dao.PersonDao;
import airplane.entity.Brigade;
import airplane.entity.Person;
import airplane.service.Service;
import airplane.service.ServiceException;
import java.util.ArrayList;
import java.util.List;

public class BrigadeServiceImpl implements Service<Brigade> {
    private BrigadeDao brigadeDao;
    private PersonDao personDao;

    public BrigadeServiceImpl(BrigadeDao brigadeDao, PersonDao personDao) {
        this.brigadeDao = brigadeDao;
        this.personDao = personDao;
    }

    @Override
    public Brigade findById(Long id) throws ServiceException {
        try {
            if (id != null) {
                return brigadeDao.read(id);
            } else {
                throw new ServiceException("No entity's id was found");
            }
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public List<Brigade> findAll() throws ServiceException {
        try {
            return brigadeDao.readAll();
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public void create(List<Brigade> entities) throws ServiceException {
        try {
            if (entities == null || entities.isEmpty()) {
                throw new ServiceException("There are no users to store");
            }
            boolean isExistInDB = true;
            for (Brigade brigade : entities) {
                for (Person person : brigade.getPersons()) {
                    if (!personDao.isPersonExist(person)) {
                        isExistInDB = false;
                    }
                }
            }
            if (isExistInDB) {
                brigadeDao.save(entities);
            } else {
                throw new ServiceException("No person was found");
            }
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public void edit(Brigade entity) throws ServiceException {
        try {
            if (entity != null && entity.getId() != null) {
                Brigade brigade = brigadeDao.read(entity.getId());
                if (brigade != null) {
                    boolean isExistInDB = true;
                    for (int i = 0; i < entity.getPersons().size(); i++) {
                        if (!brigade.getPersons().get(i).equals(entity.getPersons().get(i))) {
                            if (!personDao.isPersonExist(entity.getPersons().get(i))) {
                                isExistInDB = false;
                            }
                        }
                    }
                    if (isExistInDB) {
                        List<Person> personList = new ArrayList<>();
                        for (int i = 0; i < brigade.getPersons().size(); i++) {
                            personList.add(entity.getPersons().get(i) == null ? brigade.getPersons().get(i) :
                                    entity.getPersons().get(i));
                        }
                        brigade.setPersons(personList);
                        brigadeDao.update(brigade);
                    } else {
                        throw new ServiceException("No person was found");
                    }
                } else {
                    throw new ServiceException("No entity with this identifier was found");
                }
            } else {
                throw new ServiceException("No entity or no airplane.entity's id was found");
            }
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public void delete(Long id) throws ServiceException {
        try {
            if (id != null) {
                brigadeDao.delete(id);
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
            return brigadeDao.getMaxId();
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }
}
