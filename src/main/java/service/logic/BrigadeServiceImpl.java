package service.logic;

import dao.DaoException;
import dao.postgresql.BrigadeDaoImpl;
import dao.postgresql.PersonDaoImpl;
import entity.Brigade;
import jdk.nashorn.internal.ir.SplitReturn;
import service.Service;
import service.ServiceException;
import utils.db.Connector;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class BrigadeServiceImpl implements Service<Brigade> {
    private BrigadeDaoImpl brigadeDao;

    public BrigadeServiceImpl() throws SQLException {
        brigadeDao = new BrigadeDaoImpl(Connector.getConnection());
    }

    @Override
    public Optional<Brigade> findById(Long id) throws ServiceException {
        try {
            if (id != null) {
                return Optional.ofNullable(brigadeDao.read(id));
            } else {
                throw new ServiceException();
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
            brigadeDao.save(entities);
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
    public void delete(Long id) throws ServiceException {
        try {
            brigadeDao.delete(id);
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
