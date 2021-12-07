package service.logic;

import dao.DaoException;
import dao.postgresql.BrigadeDaoImpl;
import dao.postgresql.PersonDaoImpl;
import entity.Brigade;
import jdk.nashorn.internal.ir.SplitReturn;
import service.Service;
import service.ServiceException;

import java.util.Arrays;
import java.util.List;

public class BrigadeServiceImpl implements Service<Brigade> {
    private BrigadeDaoImpl brigadeDao;
    private PersonDaoImpl personDao;

    public BrigadeServiceImpl() {
        this.brigadeDao = new BrigadeDaoImpl();
        this.personDao = new PersonDaoImpl();
    }

    @Override
    public Brigade read(Integer id) throws ServiceException {
        try {
            if (id != null) {
                return brigadeDao.read(id);
            } else {
                throw new ServiceException();
            }
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public List<Brigade> readAll() throws ServiceException {
        try {
            return brigadeDao.readAll();
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public void save(Brigade entity) throws ServiceException {
        try {
            if (entity != null && entity.getPersons().length == entity.getIteration()) {
                brigadeDao.create(entity);
            } else {
                throw new ServiceException();
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
                    brigadeDao.update(entity);
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
        try {
            brigadeDao.delete(id);
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }
}
