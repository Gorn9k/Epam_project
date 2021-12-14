package service;

import java.util.List;
import java.util.Optional;

public interface Service<T> {
    Optional<T> findById(Long id) throws ServiceException;

    List<T> findAll() throws ServiceException;

    void create(List<T> entities) throws ServiceException;

    void edit(T entity) throws ServiceException;

    void delete(Long id) throws ServiceException;
}
