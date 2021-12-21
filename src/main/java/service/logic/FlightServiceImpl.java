package service.logic;

import dao.DaoException;
import dao.PersonDao;
import dao.postgresql.BrigadeDaoImpl;
import dao.postgresql.FlightDaoImpl;
import dao.postgresql.PersonDaoImpl;
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
    private BrigadeDaoImpl brigadeDao;

    public FlightServiceImpl() throws SQLException {
        flightDao = new FlightDaoImpl(Connector.getConnection());
        brigadeDao = new BrigadeDaoImpl(Connector.getConnection());
    }

    @Override
    public Flight findById(Long id) throws ServiceException {
        try {
            if (id != null) {
                return flightDao.read(id);
            } else {
                throw new ServiceException("No entity's id was found");
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
            if (entities == null || entities.isEmpty()) {
                throw new ServiceException("There are no users to store");
            } else {
                boolean isExistInDB = true;
                for (Flight flight : entities) {
                    if (flight.getBrigade() != null) {
                        if (brigadeDao.read(flight.getBrigade().getId()) == null){
                            isExistInDB = false;
                        }
                    }
                }
                if (isExistInDB) {
                    flightDao.save(entities);
                } else {
                    throw new ServiceException("No brigade was found");
                }
            }
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public void edit(Flight entity) throws ServiceException {
        try {
            if (entity == null && entity.getId() == null) {
                throw new ServiceException();
            }
            Flight flight = flightDao.read(entity.getId());
            if (flight == null) {
                throw new ServiceException("No entity with this identifier was found");
            }
            if (entity.getBrigade().getId() != null && brigadeDao.read(entity.getBrigade().getId()) == null) {
                throw new ServiceException("No brigade with this identifier was found");
            }
            flight.setFlightName(entity.getFlightName() == null ? flight.getFlightName() : entity.getFlightName());
            flightDao.update(entity);
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public void delete(Long id) throws ServiceException {
        try {
            if (id != null) {
                flightDao.delete(id);
            } else {
                throw new ServiceException();
            }
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
