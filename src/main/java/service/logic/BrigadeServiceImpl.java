package service.logic;

import dao.DaoException;
import dao.postgresql.BrigadeDaoImpl;
import dao.postgresql.PersonDaoImpl;
import entity.Brigade;
import entity.Person;
import service.Service;
import service.ServiceException;
import utils.db.Connector;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BrigadeServiceImpl implements Service<Brigade> {
    private BrigadeDaoImpl brigadeDao;
    private PersonDaoImpl personDao;

    public BrigadeServiceImpl() throws SQLException {
        brigadeDao = new BrigadeDaoImpl(Connector.getConnection());
        personDao = new PersonDaoImpl(Connector.getConnection());
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
                        for (int i = 0; i < Brigade.DEFAULT_SIZE_OF_BRIGADE - 2; i++) {
                            personList.add(entity.getPersons().get(i) == null ? brigade.getPersons().get(i) :
                                    entity.getPersons().get(i));
                        }
                        brigade.setPersons(personList);
                        brigadeDao.update(entity);
                    } else {
                        throw new ServiceException("No person was found");
                    }
                } else {
                    throw new ServiceException("No entity with this identifier was found");
                }
            } else {
                throw new ServiceException("No entity or no entity's id was found");
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
