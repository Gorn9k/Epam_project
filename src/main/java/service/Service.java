package service;

import java.util.List;

public interface Service<T> {
    T read(Long id);
    List<T> readAll();
    void save(T entity);
    void delete(Long id);
    void edit(T entity);
}
