package service;

import java.util.List;
import java.util.UUID;

public interface Service<T> {
    T read(Integer id) throws ServiceException;

    List<T> readAll() throws ServiceException;

    void save(T entity) throws ServiceException;

    void edit(T entity) throws ServiceException;

    void delete(Integer id) throws ServiceException;
}
