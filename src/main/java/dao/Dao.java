package dao;

import entity.Entity;
import utils.db.StatementSetter;
import java.sql.*;
import java.util.List;

public interface Dao<T extends Entity> {
    T read(Long id) throws DaoException;

    void save(List<T> entities) throws DaoException;

    void update(T entity) throws DaoException;

    void delete(Long id) throws DaoException;

    List<T> readAll() throws DaoException;

    default void create(String sql, List<T> entities, Connection connection, StatementSetter<T> statementSetter) throws DaoException {
        if (entities == null || entities.size() == 0) {
            throw new DaoException("There is no users for create");
        }
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (T entity : entities) {
                statementSetter.setStatement(statement, entity);
                statement.addBatch();
            }
            statement.clearParameters();
            statement.executeBatch();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    default void update(String sql, T entity, Connection connection, StatementSetter<T> statementSetter) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statementSetter.setStatement(statement, entity);
            statement.executeUpdate();
        } catch(SQLException e) {
            throw new DaoException(e);
        }
    }

    default void delete(String sql, Connection connection) throws DaoException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch(SQLException e) {
            throw new DaoException(e);
        }
    }
}
