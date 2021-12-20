package service.logic;

import dao.DaoException;
import dao.postgresql.FlightDaoImpl;
import entity.Flight;
import entity.Person;
import service.Service;
import service.ServiceException;
import utils.db.Connector;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class FlightServiceImpl implements Service<Flight> {
    private FlightDaoImpl flightDao;

    public FlightServiceImpl() throws SQLException {
        flightDao = new FlightDaoImpl(Connector.getConnection());
    }

    @Override
    public Optional<Flight> findById(Long id) throws ServiceException {
        try {
            if (id != null) {
                return Optional.ofNullable(flightDao.read(id));
            } else {
                throw new ServiceException();
            }
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public List<Flight> findAll() throws ServiceException {
        try {
            return flightDao.readAll();
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public void create(List<Flight> entities) throws ServiceException {
        try {
            if (entities != null) {
                flightDao.save(entities);
            } else {
                throw new ServiceException();
            }
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public void edit(Flight entity) throws ServiceException {
        try {
            if (entity != null && entity.getId() != null) {
                flightDao.update(entity);
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
            flightDao.delete(id);
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public Long findMaxId() throws ServiceException {
        try {
            return flightDao.getMaxId();
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }
}
