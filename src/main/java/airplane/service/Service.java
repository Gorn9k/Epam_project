package airplane.service;

import java.util.List;

public interface Service<T> {
    T findById(Long id) throws ServiceException;

    List<T> findAll() throws ServiceException;

    void create(List<T> entities) throws ServiceException;

    void edit(T entity) throws ServiceException;

    void delete(Long id) throws ServiceException;

    Long findMaxId() throws ServiceException;
}
