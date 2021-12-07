package service.logic;

import dao.DaoException;
import dao.postgresql.FlightDaoImpl;
import entity.Flight;
import service.Service;
import service.ServiceException;

import java.util.List;

public class FlightServiceImpl implements Service<Flight> {
    private FlightDaoImpl flightDao;

    public FlightServiceImpl() {
        this.flightDao = new FlightDaoImpl();
    }

    @Override
    public Flight read(Integer id) throws ServiceException {
        try {
            if (id != null) {
                return flightDao.read(id);
            } else {
                throw new ServiceException();
            }
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public List<Flight> readAll() throws ServiceException {
        try {
            return flightDao.readAll();
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public void save(Flight entity) throws ServiceException {
        try {
            if (entity != null) {
                flightDao.create(entity);
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
    public void delete(Integer id) throws ServiceException {
        try {
            flightDao.delete(id);
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }
}
