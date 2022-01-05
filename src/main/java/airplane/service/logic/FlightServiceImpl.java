package airplane.service.logic;

import airplane.dao.*;
import airplane.entity.Flight;
import airplane.service.Service;
import airplane.service.ServiceException;

import java.util.List;

public class FlightServiceImpl implements Service<Flight> {
    private FlightDao flightDao;
    private BrigadeDao brigadeDao;

    public FlightServiceImpl(FlightDao flightDao, BrigadeDao brigadeDao) {
        this.flightDao = flightDao;
        this.brigadeDao = brigadeDao;
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
                throw new ServiceException("Try to update a not existed flight");
            }
            Flight flight = flightDao.read(entity.getId());
            if (flight == null) {
                throw new ServiceException("No entity with this identifier was found");
            }
            if (entity.getBrigade() != null && entity.getBrigade().getId() != null && brigadeDao.read(entity.getBrigade().getId()) == null) {
                throw new ServiceException("No brigade with this identifier was found");
            }
            flight.setFlightName(entity.getFlightName() == null ? flight.getFlightName() : entity.getFlightName());
            flightDao.update(flight);
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
