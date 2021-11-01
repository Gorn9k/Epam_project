package dao;

import java.util.UUID;

public interface Dao<T> {
    T read(UUID id) throws DaoException;

    Long create(T entity) throws DaoException;

    void update(T entity) throws DaoException;

    void delete(UUID id) throws DaoException;
}
