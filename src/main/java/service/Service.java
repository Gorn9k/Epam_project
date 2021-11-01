package service;

import java.util.List;
import java.util.UUID;

public interface Service<T> {
    T read(UUID id);
    List<T> readAll();
    void save(T entity);
    void delete(UUID id);
    void edit(T entity);
}
