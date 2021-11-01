package dao.postgresql;

import dao.DaoException;
import dao.FlightDao;
import entity.Flight;

import java.util.List;
import java.util.UUID;

public class FlightDaoImpl implements FlightDao {
    @Override
    public Flight read(UUID id) throws DaoException {
        return null;
    }

    @Override
    public Long create(Flight entity) throws DaoException {
        return null;
    }

    @Override
    public void update(Flight entity) throws DaoException {

    }

    @Override
    public void delete(UUID id) throws DaoException {

    }

    @Override
    public List<Flight> readAll() {
        return null;
    }
}
